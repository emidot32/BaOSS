from __init__ import db


class User(db.Model):
    __tablename__ = 'users'
    user_id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String, nullable=False)
    surname = db.Column(db.String, nullable=False)
    email = db.Column(db.String(120), unique=True, nullable=False)
    login = db.Column(db.String(120), unique=True, nullable=False)
    password = db.Column(db.String(120), nullable=False)
    phone_number = db.Column(db.String(15))
    usr_role = db.Column(db.String(50))
    birthday = db.Column(db.DateTime)
    balance = db.Column(db.Float)
    reg_date = db.Column(db.DateTime)


class Order(db.Model):
    __tablename__ = 'orders'
    order_id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('users.user_id'), nullable=False)
    status = db.Column(db.String, nullable=False)
    start_date = db.Column(db.DateTime)
    completion_date = db.Column(db.DateTime)
    total_nrc = db.Column(db.Float)
    total_mrc = db.Column(db.Float)
    order_aim = db.Column(db.String, nullable=False)
    products = db.Column(db.String)


class Instance(db.Model):
    __tablename__ = 'instances'
    instance_id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('users.user_id'), nullable=False)
    status = db.Column(db.String, nullable=False)
    activated_date = db.Column(db.DateTime)
    disconnected_date = db.Column(db.DateTime)


class Employee(db.Model):
    __tablename__ = 'employees'
    empl_id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('users.user_id'), nullable=False)
    position = db.Column(db.String, nullable=False)
    empl_date = db.Column(db.DateTime)
    dismissal_date = db.Column(db.DateTime)
    salary = db.Column(db.Integer)


class MobileProduct(db.Model):
    __tablename__ = 'mobile_products'
    mob_prod_id = db.Column(db.Integer, primary_key=True)
    instance_id = db.Column(db.Integer, db.ForeignKey('instances.instance_id'), nullable=False)
    tariff_id = db.Column(db.String)
    phone_number = db.Column(db.String)
    balance = db.Column(db.Integer)
    support_5g = db.Column(db.Boolean)


class InternetProduct(db.Model):
    __tablename__ = 'internet_products'
    inet_prod_id = db.Column(db.Integer, primary_key=True)
    instance_id = db.Column(db.Integer, db.ForeignKey('instances.instance_id'), nullable=False)
    address_id = db.Column(db.Integer)
    internet_offer_id = db.Column(db.String)
    fixed_ip = db.Column(db.String)
    cable_len = db.Column(db.Integer)
    ipv6_support = db.Column(db.Boolean)


class DtvProduct(db.Model):
    __tablename__ = 'dtv_products'
    dtv_prod_id = db.Column(db.Integer, primary_key=True)
    instance_id = db.Column(db.Integer, db.ForeignKey('instances.instance_id'), nullable=False)
    inet_prod_id = db.Column(db.Integer, db.ForeignKey('internet_products.inet_prod_id'), nullable=False)
    dtv_offer_id = db.Column(db.String)


class Payment(db.Model):
    __tablename__ = 'payments'
    payment_id = db.Column(db.Integer, primary_key=True)
    from_user = db.Column(db.Integer, db.ForeignKey('users.user_id'), nullable=False)
    value = db.Column(db.Float, nullable=False)
    payment_date = db.Column(db.DateTime)
    purpose = db.Column(db.String)
