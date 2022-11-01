FROM tiangolo/uwsgi-nginx-flask:python3.9
#RUN mkdir /app
#WORKDIR /app
#ADD requirements.txt /app
#ADD wsgi.py /app
COPY . /home/analytics-service
WORKDIR /home/analytics-service
#RUN apk add gfortran
#RUN apk add libc-dev
RUN python3 -m pip install -U pip
RUN pip3 install -r requirements.txt
CMD ["gunicorn", "-w 4", "-b", "0.0.0.0:8086", "wsgi:app"]