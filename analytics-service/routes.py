from flask import request, jsonify, Blueprint
from flask import current_app as app
from datetime import datetime
from sqlalchemy import func
from models import *
import pandas as pd
import numpy as np

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
y ='y'
step_to_pattern = {day: "%d-%m-%Y", week: "%d-%m-%Y", month: "%m-%Y", quarter: "%m-%Y", year: "%Y"}


@app.route('/cohort-analysis', methods=['GET'])
def cohort_analysis():
    return jsonify({
        "userNumber": get_user_number(),
        "userNumberByProduct": get_user_number_by_product(),
        "usersByDate": get_users_by_date(),
        "productsByDate": get_products_by_date()
    })


@app.route('/business-metrics', methods=['GET'])
def business_metrics():
    return jsonify({
        "profitByDate": get_profit_by_date(),
        "profit": get_profit(),
        "aovsByDate": get_aovs_by_date(),
        "aovs": get_aovs(),
        "arppuByDate": get_arppu_by_date(),
        "arppu": get_arppu()
    })


# @app.route('/statistic/user-number', methods=['GET'])
def get_user_number():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(User.reg_date)).first()[0])
    return len(User.query.filter(User.reg_date >= start_date).filter(User.reg_date <= end_date).all())


# @app.route('/statistic/user-number/products', methods=['GET'])
def get_user_number_by_product():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Order.completion_date)).first()[0])
    return get_number_of_users_by_products(start_date, end_date)


# @app.route('/statistic/users-by-date', methods=['GET'])
def get_users_by_date():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(User.reg_date)).first()[0])
    step = request.args.get('step') if request.args.get('step') is not None else day
    users = User.query.filter(User.reg_date >= start_date)\
        .filter(User.reg_date <= end_date).all()
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
    payments = Payment.query.filter(Payment.payment_date >= start_date)\
        .filter(Payment.payment_date <= end_date).all()
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
    orders = Order.query\
        .filter(Order.completion_date is not None)\
        .filter(Order.completion_date >= start_date)\
        .filter(Order.completion_date <= end_date).all()
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
    payments = Payment.query.filter(Payment.payment_date >= start_date)\
        .filter(Payment.payment_date <= end_date).all()
    return get_arppu_grouped_by_date(payments, step)


# @app.route('/statistic/arppu', methods=['GET'])
def get_arppu():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(User.reg_date)).first()[0])
    payments = Payment.query.filter(Payment.payment_date >= start_date).filter(Payment.payment_date <= end_date).all()
    payment_values = [payment.value for payment in payments]
    user_number = len(set([payment.from_user for payment in payments]))
    return round(sum(payment_values)/user_number, 2)


def get_date_range(func_for_start_date):
    start_date_str = request.args.get('start_date')
    end_date_str = request.args.get('end_date')
    start_date = datetime.strptime(start_date_str, date_pattern) if start_date_str is not None and start_date_str != '' else None
    end_date = datetime.strptime(end_date_str, date_pattern) if end_date_str is not None and end_date_str != '' else None
    return start_date if start_date is not None else func_for_start_date(),\
           end_date if end_date is not None else datetime.now()


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
        .filter(Order.completion_date is not None)\
        .filter(Order.completion_date >= start_date)\
        .filter(Order.completion_date <= end_date).all()
    return get_nums_grouped_by_date(products, step, 'completion_date', 'order_id', lambda df: df.count())


def get_nums_grouped_by_date(values, step, date_field, value_field, apply_to_group):
    df = pd.DataFrame.from_records([v.__dict__ for v in values])[[date_field, value_field]]
    date_to_num = apply_to_group(df.groupby(pd.Grouper(key=date_field, axis=0, freq=step[0].upper())))
    dates = [order_date.to_pydatetime().strftime(step_to_pattern[step])
             for order_date in date_to_num.index.get_level_values(date_field).tolist()]
    values = np.array(date_to_num[value_field].values.tolist())
    values[np.isnan(values)] = 0
    return {x: dates, y: [float(v) for v in values]}


def get_arppu_grouped_by_date(values, step):
    df = pd.DataFrame.from_records([v.__dict__ for v in values])[['payment_date', 'value', 'from_user']]
    date_to_sum = df[['payment_date', 'value']].groupby(pd.Grouper(key='payment_date', axis=0, freq=step[0].upper())).sum()
    date_to_users = df[['payment_date', 'from_user']].groupby(pd.Grouper(key='payment_date', axis=0, freq=step[0].upper())).agg(set)
    dates = [order_date.to_pydatetime().strftime(step_to_pattern[step]) for order_date in date_to_sum.index.get_level_values('payment_date').tolist()]
    sums = list(date_to_sum['value'].values)
    list_of_grouped_users = list(date_to_users['from_user'].values)
    values = [round((0 if np.isnan(sums[i]) else sums[i]) / len(list_of_grouped_users[i]), 2) for i in range(len(sums))]
    return {x: dates, y: values}

