from datetime import datetime
from datetime import timedelta
from dateutil.relativedelta import relativedelta
import numpy as np
import pandas as pd
from flask import current_app as app, Response
from flask import request, jsonify, json
from sqlalchemy import func
from sklearn.linear_model import Lasso
from sklearn.linear_model import LinearRegression
from sklearn.tree import DecisionTreeRegressor
from sklearn.ensemble import GradientBoostingRegressor
from sktime.forecasting.base import ForecastingHorizon
from sktime.forecasting.fbprophet import Prophet
from sktime.forecasting.naive import NaiveForecaster
from sktime.forecasting.compose import make_reduction
from sktime.forecasting.model_selection import temporal_train_test_split
from sktime.performance_metrics.forecasting import mean_absolute_percentage_error
from models import *

date_pattern = "%d-%m-%Y"
internet_product = "Internet Product"
mobile_product = "Mobile Product"
dtv_product = "DTV Product"
day = "day"
week = "week"
month = "month"
quarter = "quarter"
year = "year"
x = 'x'
y = 'y'
step_to_pattern = {day: "%d-%m-%Y", week: "%d-%m-%Y", month: "%m-%Y", quarter: "%m-%Y", year: "%Y"}
step_to_period = {day: 1, week: 7, month: 30, quarter: 90, year: 365}
end_date_condition_to_result = {
    "010": lambda end_date_param: datetime.now() + relativedelta(months=+2),
    "000": lambda end_date_param: datetime.now(),
    "100": lambda end_date_param: end_date_param,
    "101": lambda end_date_param: datetime.now(),
    "110": lambda end_date_param: datetime.now() + relativedelta(months=+2),
    "111": lambda end_date_param: end_date_param
}


@app.route('/cohort-analysis', methods=['GET'])
def cohort_analysis():
    return jsonify({
        "userNumber": get_user_number(),
        "userNumberByProducts": get_user_number_by_product(),
        "usersByDate": get_users_by_date(),
        "productsByDate": get_products_by_date(),
    })


@app.route('/business-metrics', methods=['GET'])
def business_metrics():
    return jsonify({
        "profitByDate": get_profit_by_date(),
        "profit": get_profit(),
        "aovsByDate": get_aovs_by_date(),
        "aovs": get_aovs(),
        "arppuByDate": get_arppu_by_date(),
        "arppu": get_arppu(),
        "profitByProduct": get_profits_by_product(),
        "profitByProductAndDate": get_profits_by_product_and_date(),
        "clv": get_clv(),
        "clvByDate": get_clv_by_date()
    })


@app.route('/profit-forecast', methods=['GET'])
def profit_forecast():
    try:
        forecast = get_profit_forecast()
    except NotEnoughDataForPrediction:
        response = Response(status=404)
        response.data = json.dumps({"timestamp": datetime.now(),
                         "status": 404,
                         "error": "Not Found",
                         "message": "Not enough data for prediction"})
        return response
    return jsonify({
        "profitForecast": forecast[0],
        "forecastWithTest": forecast[1],
        "mapeEvaluation": forecast[2],
        "regressors": ["Prophet", "Lasso", "Decision Tree", "Gradient Boosting"]
    })


# @app.route('/statistic/user-number', methods=['GET'])
def get_user_number():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(User.reg_date)).first()[0])
    return len(User.query.filter(User.reg_date >= start_date).filter(User.reg_date <= end_date).filter(
        User.usr_role == 'USER').all())


# @app.route('/statistic/user-number/products', methods=['GET'])
def get_user_number_by_product():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Order.completion_date)).first()[0])
    return get_number_of_users_by_products(start_date, end_date)


# @app.route('/statistic/users-by-date', methods=['GET'])
def get_users_by_date():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(User.reg_date)).first()[0])
    step = request.args.get('step') if request.args.get('step') is not None else day
    users = User.query.filter(User.reg_date >= start_date) \
        .filter(User.reg_date <= end_date) \
        .filter(User.usr_role == 'USER').all()
    if len(users) == 0:
        return {x: [], y: []}
    return get_nums_grouped_by_date(users, step, 'reg_date', 'user_id', lambda df: df.count())


# @app.route('/statistic/products-by-date', methods=['GET'])
def get_products_by_date():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Order.completion_date)).first()[0])
    step = request.args.get('step') if request.args.get('step') is not None else day
    date_and_product_nums = [get_product_numbers_by_date(start_date, end_date, step, mobile_product),
                             get_product_numbers_by_date(start_date, end_date, step, internet_product),
                             get_product_numbers_by_date(start_date, end_date, step, dtv_product)]
    return date_and_product_nums


# @app.route('/statistic/profit-by-date', methods=['GET'])
def get_profit_by_date():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Payment.payment_date)).first()[0])
    step = request.args.get('step') if request.args.get('step') is not None else day
    payments = Payment.query.filter(Payment.payment_date >= start_date) \
        .filter(Payment.payment_date <= end_date).all()
    if len(payments) == 0:
        return {x: [], y: []}
    return get_nums_grouped_by_date(payments, step, 'payment_date', 'value', lambda df: df.sum())


# @app.route('/statistic/profit', methods=['GET'])
def get_profit():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Payment.payment_date)).first()[0])
    payment_values = [res[0] for res in Payment.query.filter(Payment.payment_date >= start_date)
    .filter(Payment.payment_date <= end_date).with_entities(Payment.value).all()]
    return round(sum(payment_values), 2)


# @app.route('/statistic/aovs-by-date', methods=['GET'])
def get_aovs_by_date():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Order.completion_date)).first()[0])
    step = request.args.get('step') if request.args.get('step') is not None else day
    orders = Order.query \
        .filter(Order.completion_date is not None) \
        .filter(Order.completion_date >= start_date) \
        .filter(Order.completion_date <= end_date).all()
    if len(orders) == 0:
        return [{x: [], y: []}, {x: [], y: []}]
    aovs_by_date = [get_nums_grouped_by_date(orders, step, 'completion_date', 'total_nrc', lambda df: df.mean()),
                    get_nums_grouped_by_date(orders, step, 'completion_date', 'total_mrc', lambda df: df.mean())]
    return aovs_by_date


# @app.route('/statistic/aovs', methods=['GET'])
def get_aovs():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Order.completion_date)).first()[0])
    orders = Order.query \
        .filter(Order.completion_date is not None) \
        .filter(Order.completion_date >= start_date) \
        .filter(Order.completion_date <= end_date).all()
    mean_nrc = np.array([order.total_nrc for order in orders]).mean()
    mean_mrc = np.array([order.total_mrc for order in orders]).mean()
    return round(mean_nrc, 2), round(mean_mrc, 2)


# @app.route('/statistic/arppu-by-date', methods=['GET'])
def get_arppu_by_date():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Payment.payment_date)).first()[0])
    step = request.args.get('step') if request.args.get('step') is not None else day
    payments = Payment.query.filter(Payment.payment_date >= start_date) \
        .filter(Payment.payment_date <= end_date).all()
    if len(payments) == 0:
        return {x: [], y: []}
    return get_arppu_grouped_by_date(payments, step)


# @app.route('/statistic/arppu', methods=['GET'])
def get_arppu():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(User.reg_date)).first()[0])
    payments = Payment.query.filter(Payment.payment_date >= start_date).filter(Payment.payment_date <= end_date).all()
    payment_values = [payment.value for payment in payments]
    user_number = len(set([payment.from_user for payment in payments]))
    return round(sum(payment_values) / user_number, 2)


def get_profits_by_product():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Payment.payment_date)).first()[0])
    payments = Payment.query.filter(Payment.payment_date >= start_date).filter(Payment.payment_date <= end_date).all()
    if len(payments) == 0:
        return {x: [], y: []}
    product_to_profit = dict()
    product_to_profit[mobile_product] = round(
        sum([payment.value for payment in payments if mobile_product in payment.purpose]), 2)
    product_to_profit[internet_product] = round(
        sum([payment.value for payment in payments if internet_product in payment.purpose]), 2)
    product_to_profit[dtv_product] = round(
        sum([payment.value for payment in payments if dtv_product in payment.purpose]), 2)
    return {x: list(product_to_profit.keys()), y: list(product_to_profit.values())}


def get_profits_by_product_and_date():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Payment.payment_date)).first()[0])
    step = request.args.get('step') if request.args.get('step') is not None else day
    all_payments = Payment.query.filter(Payment.payment_date >= start_date).filter(Payment.payment_date <= end_date)
    mobile_payments = all_payments.filter(Payment.purpose.contains(mobile_product)).all()
    internet_payments = all_payments.filter(Payment.purpose.contains(internet_product)).all()
    dtv_payments = all_payments.filter(Payment.purpose.contains(dtv_product)).all()
    return [get_nums_grouped_by_date(mobile_payments, step, 'payment_date', 'value', lambda df: df.sum()) if len(
        mobile_payments) > 0 else {x: [], y: []},
            get_nums_grouped_by_date(internet_payments, step, 'payment_date', 'value', lambda df: df.sum()) if len(
                internet_payments) > 0 else {x: [], y: []},
            get_nums_grouped_by_date(dtv_payments, step, 'payment_date', 'value', lambda df: df.sum()) if len(
                dtv_payments) > 0 else {x: [], y: []}]


def get_clv():
    return round(get_arppu() * 0.8 * 4, 2)


def get_clv_by_date():
    arppu_by_date = get_arppu_by_date()
    arppu_by_date[y] = [round((arppu * 0.8 * 4), 2) for arppu in arppu_by_date[y]]
    return arppu_by_date


def get_profit_forecast():
    start_date, forecast_end_date = get_date_range(lambda: db.session.query(func.min(Payment.payment_date)).first()[0], True)
    step = request.args.get('step') if request.args.get('step') is not None else day
    start_date = get_start_date_by_step(step)
    payment_end_date = get_payments_end_date(step)
    payments = Payment.query.filter(Payment.payment_date >= start_date) \
        .filter(Payment.payment_date < payment_end_date).order_by(Payment.payment_date).all()
    if len(payments) == 0:
        return {x: [], y: []}
    df = pd.DataFrame.from_records([v.__dict__ for v in payments])[['payment_date', 'value']]
    date_to_profit = df.groupby(pd.Grouper(key='payment_date', axis=0, freq=step[0].upper())).sum()
    forecast_with_test, mape_results = get_sktime_forecast(date_to_profit, payment_end_date, forecast_end_date, step, True)
    return get_sktime_forecast(date_to_profit, payment_end_date, forecast_end_date, step, False)[0], forecast_with_test, mape_results


def get_date_range(func_for_start_date, for_forecast=False):
    start_date_str = request.args.get('start_date')
    end_date_str = request.args.get('end_date')
    start_date = datetime.strptime(start_date_str,
                                   date_pattern) if start_date_str is not None and start_date_str != '' else None
    end_date = datetime.strptime(end_date_str,
                                 date_pattern) if end_date_str is not None and end_date_str != '' else None
    start_date = start_date if start_date is not None else func_for_start_date()
    condition = str(int(end_date is not None)) + str(int(for_forecast)) + str(int(end_date > datetime.now() if end_date is not None else False))
    return start_date if start_date is not None else func_for_start_date(), end_date_condition_to_result[condition](end_date)


def get_number_of_users_by_products(start_date, end_date):
    product_to_number = dict()
    product_to_number[mobile_product] = len(set(Order.query.filter(Order.products.contains(mobile_product))
                                                .filter(Order.completion_date is not None)
                                                .filter(Order.completion_date >= start_date)
                                                .filter(Order.completion_date <= end_date)
                                                .with_entities(Order.user_id).all()))
    product_to_number[internet_product] = len(set(Order.query.filter(Order.products.contains(internet_product))
                                                  .filter(Order.completion_date is not None)
                                                  .filter(Order.completion_date >= start_date)
                                                  .filter(Order.completion_date <= end_date)
                                                  .with_entities(Order.user_id).all()))
    product_to_number[dtv_product] = len(set(Order.query.filter(Order.products.contains(dtv_product))
                                             .filter(Order.completion_date is not None)
                                             .filter(Order.completion_date >= start_date)
                                             .filter(Order.completion_date <= end_date)
                                             .with_entities(Order.user_id).all()))
    return {x: list(product_to_number.keys()), y: list(product_to_number.values())}


def get_product_numbers_by_date(start_date, end_date, step, product):
    products = Order.query.filter(Order.products.contains(product)) \
        .filter(Order.completion_date is not None) \
        .filter(Order.completion_date >= start_date) \
        .filter(Order.completion_date <= end_date).all()
    if len(products) == 0:
        return {x: [], y: []}
    return get_nums_grouped_by_date(products, step, 'completion_date', 'order_id', lambda df: df.count())


def get_nums_grouped_by_date(values, step, date_field, value_field, apply_to_group):
    df = pd.DataFrame.from_records([v.__dict__ for v in values])[[date_field, value_field]]
    date_to_num = apply_to_group(df.groupby(pd.Grouper(key=date_field, axis=0, freq=step[0].upper())))
    dates = [order_date.strftime(step_to_pattern[step])
             for order_date in date_to_num.index.get_level_values(date_field).tolist()]
    values = np.array(date_to_num[value_field].values.tolist())
    values[np.isnan(values)] = 0
    return {x: dates, y: [round(float(v), 2) for v in values]}


def get_arppu_grouped_by_date(values, step):
    df = pd.DataFrame.from_records([v.__dict__ for v in values])[['payment_date', 'value', 'from_user']]
    date_to_sum = df[['payment_date', 'value']].groupby(
        pd.Grouper(key='payment_date', axis=0, freq=step[0].upper())).sum()
    date_to_users = df[['payment_date', 'from_user']].groupby(
        pd.Grouper(key='payment_date', axis=0, freq=step[0].upper())).agg(set)
    dates = [order_date.strftime(step_to_pattern[step]) for order_date in
             date_to_sum.index.get_level_values('payment_date').tolist()]
    sums = list(date_to_sum['value'].values)
    list_of_grouped_users = list(date_to_users['from_user'].values)
    values = [calculate_arppu_by_date(list_of_grouped_users, sums, i) for i in range(len(sums))]
    return {x: dates, y: values}


def calculate_arppu_by_date(list_of_grouped_users, sums, i):
    unique_users = len(list_of_grouped_users[i])
    if unique_users == 0:
        return 0
    sum = 0 if np.isnan(sums[i]) else sums[i]
    return round(sum / unique_users, 2)


def get_sktime_forecast(date_to_profit, payment_end_date, forecast_end_date, step, with_test):
    if len(date_to_profit) < 15:
        raise NotEnoughDataForPrediction()
    y_train, y_test = temporal_train_test_split(date_to_profit, test_size=int(0.2*len(date_to_profit)) if with_test else 1)
    fh = ForecastingHorizon(pd.date_range(start=payment_end_date, end=forecast_end_date, freq=step[0].upper()), is_relative=False) \
        if not with_test else ForecastingHorizon(y_test.index, is_relative=False)
    result_data = []
    dates = [grouped_date.strftime(step_to_pattern[step])
             for grouped_date in date_to_profit.index.get_level_values('payment_date').tolist()]
    grouped_values = np.array(date_to_profit['value'].values.tolist())
    grouped_values[np.isnan(grouped_values)] = 0
    result_data.append({x: dates, y: [round(float(v), 2) for v in grouped_values]})

    mape_results = []
    # NaiveForecaster(sp=12, strategy='last'),
    regressors = [Prophet(), make_reduction(Lasso()), make_reduction(DecisionTreeRegressor()), make_reduction(GradientBoostingRegressor())]
    for regressor in regressors:
        if not with_test:
            result_data.append(fit_predict(regressor, y_train, fh, step)[0])
        else:
            prediction, mape_res = fit_predict(regressor, y_train, fh, step, y_test)
            result_data.append(prediction)
            mape_results.append(round(mape_res, 2))
    return result_data, mape_results


def fit_predict(regressor, train_data, fh, step, test_data=None):
    regressor.fit(train_data)
    profit_forecast_result = regressor.predict(fh)
    profit_forecast_result = profit_forecast_result.fillna(0)
    dates = [grouped_date.strftime(step_to_pattern[step])
             for grouped_date in profit_forecast_result.index.tolist()]
    values = np.array(profit_forecast_result['value'].values.tolist())
    values[np.isnan(values)] = 0
    results = {x: dates, y: [round(float(v), 2) for v in values]}
    return results, mean_absolute_percentage_error(test_data, profit_forecast_result, symmetric=False) if test_data is not None else []


def get_start_date_by_step(step):
    if step == day:
        return datetime.now() + relativedelta(months=-2)
    elif step == week:
        return datetime.now() + relativedelta(months=-12)
    elif step == month:
        return datetime.now() + relativedelta(years=-3)
    else:
        return db.session.query(func.min(Payment.payment_date)).first()[0]


def get_payments_end_date(step):
    now = datetime.now()
    if step == day:
        return now.replace(hour=23, minute=59, second=59) - timedelta(days=1)
    elif step == week:
        return (now - timedelta(days=now.weekday())) - timedelta(days=1)
    elif step == month:
        return now.replace(day=1, hour=23, minute=59, second=59) - timedelta(days=1)
    elif step == quarter:
        return datetime(now.year, 3 * ((now.month - 1) // 3) + 1, 1) - timedelta(days=1)
    else:
        return now.replace(month=1, day=1, hour=23, minute=59, second=59) - timedelta(days=1)
