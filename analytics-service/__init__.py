"""Initialize Flask app."""
from flask import Flask
from flask_cors import CORS
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()


def create_app():
    """Construct the core application."""
    app = Flask(__name__)
    app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://postgres:Aposuv74@localhost:5432/baoss_db'
    #app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://postgres:Aposuv74@postgres-db:5432/baoss_db'
    app.config['CORS_HEADERS'] = 'Content-Type'
    CORS(app)
    db.init_app(app)

    with app.app_context():
        import routes
        return app
