# Usa l'immagine di Node.js come base
FROM node:18

# Crea e imposta la directory di lavoro
WORKDIR /usr/src/app

# Copia i file di configurazione di Node.js (package.json e package-lock.json)
COPY package*.json ./

# Installa le dipendenze del progetto
RUN npm install

# Copia tutto il resto dell'app nella directory di lavoro nel container
COPY . .

# Esponi la porta su cui il server sarà in ascolto
EXPOSE 3000

# Comando di avvio dell'applicazione
CMD ["node", "app.js"]