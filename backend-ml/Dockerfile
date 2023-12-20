# Gunakan image Python resmi yang ringan
FROM python:3.8-slim

# Atur direktori kerja di dalam kontainer
WORKDIR /app

# Salin isi direktori saat ini ke dalam kontainer di /app
COPY . /app

# Instal paket yang diperlukan yang tercantum di requirements.txt
RUN apt-get update && apt-get install -y python3-venv
RUN python3 -m venv venv
RUN . venv/bin/activate
RUN pip install --trusted-host pypi.python.org -r requirements.txt

# Membuat port 5000 tersedia untuk dunia di luar kontainer
EXPOSE 5000

# Menjalankan app.py saat kontainer diluncurkan
CMD ["python", "app.py"]
