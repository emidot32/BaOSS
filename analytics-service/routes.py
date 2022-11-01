from flask import request, jsonify, Blueprint
from flask import current_app as app
from datetime import datetime
from sqlalchemy import func
from models import *
import pandas as pd

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
step_to_pattern = {day: "%d-%m-%Y", week: "%d-$m-$Y", month: "%m-%Y", quarter: "%m-%Y", year: "%Y"}


@app.route('/statistic/user-number', methods=['GET'])
def user_number():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(User.reg_date)))
    return jsonify(len(User.query.filter(User.reg_date >= start_date).filter(User.reg_date <= end_date).all()))


@app.route('/statistic/user-number/products', methods=['GET'])
def user_number_by_product():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Order.completion_date)))
    return jsonify(get_number_of_users_by_products(start_date, end_date))


@app.route('/statistic/users-by-date', methods=['GET'])
def users_by_date():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(User.reg_date)))
    step = request.args.get('step') if request.args.get('step') is not None else day
    users = User.query.filter(User.reg_date >= start_date)\
        .filter(User.reg_date <= end_date).all()
    return jsonify(get_nums_grouped_by_date(users, step, 'reg_date', 'user_id'))


@app.route('/statistic/products-by-date', methods=['GET'])
def products_by_date():
    start_date, end_date = get_date_range(lambda: db.session.query(func.min(Order.completion_date)))
    step = request.args.get('step') if request.args.get('step') is not None else day
    date_and_product_nums = [get_product_numbers_by_date(start_date, end_date, step, mobile_product),
                             get_product_numbers_by_date(start_date, end_date, step, internet_product),
                             get_product_numbers_by_date(start_date, end_date, step, dtv_product)]
    return jsonify(date_and_product_nums)


def get_date_range(func_for_start_date):
    start_date_str = request.args.get('start_date')
    end_date_str = request.args.get('end_date')
    start_date = datetime.strptime(start_date_str, date_pattern) if start_date_str is not None else None
    end_date = datetime.strptime(end_date_str, date_pattern) if end_date_str is not None else None
    return start_date if start_date is not None else func_for_start_date(),\
           end_date if end_date is not None else datetime.now()


def get_number_of_users_by_products(start_date, end_date):
    product_to_number = dict()
    product_to_number[mobile_product] = len(set(Order.query.filter(Order.products.contains(mobile_product))
                                                .filter(Order.completion_date >= start_date)
                                                .filter(Order.completion_date <= end_date)
                                                .with_entities(Order.user_id).all()))
    product_to_number[internet_product] = len(set(Order.query.filter(Order.products.contains(internet_product))
                                                  .filter(Order.completion_date >= start_date)
                                                  .filter(Order.completion_date <= end_date)
                                                  .with_entities(Order.user_id).all()))
    product_to_number[dtv_product] = len(set(Order.query.filter(Order.products.contains(dtv_product))
                                             .filter(Order.completion_date >= start_date)
                                             .filter(Order.completion_date <= end_date)
                                             .with_entities(Order.user_id).all()))
    return {x: product_to_number.keys(), y: product_to_number.values()}


def get_product_numbers_by_date(start_date, end_date, step, product):
    products = Order.query.filter(Order.products.contains(product))\
        .filter(Order.completion_date >= start_date)\
        .filter(Order.completion_date <= end_date).all()
    return get_nums_grouped_by_date(products, step, 'completion_date', 'order_id')


def get_nums_grouped_by_date(values, step, date_field, id_field):
    df = pd.DataFrame.from_records([v.__dict__ for v in values])[[date_field, id_field]]
    date_to_num = df.groupby(pd.Grouper(key=date_field, axis=0, freq=step[0].upper())).count()
    dates = [order_date.to_pydatetime().strftime(step_to_pattern[step])
             for order_date in date_to_num.index.get_level_values(date_field).tolist()]
    return {x: dates, y: date_to_num[id_field].values.tolist()}

