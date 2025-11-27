TP Microservices - WebSocket & gRPC

Ce repository contient deux projets démontrant différentes technologies de communication temps réel et RPC.

📋 Projets

🚀 ms-websocket-jhipster/

Application de Chat en Temps Réel avec Interface Cyberpunk

Une application WebSocket complète avec dashboard interactif et interface futuriste.

🛠️ Technologies

Backend: Spring Boot, WebSocket, STOMP, JHipster
Frontend: HTML5, CSS3, JavaScript, Chart.js
Style: Thème cyberpunk avec animations
Communication: WebSocket pour temps réel
⚡ Fonctionnalités

💬 Chat en temps réel avec interface cyberpunk
🔔 Système de notifications temps réel
📊 Dashboard avec métriques en direct
👥 Gestion des utilisateurs connectés
🎨 Interface visuelle avancée avec effets neon
📈 Graphiques et analytics en temps réel
🚀 Démarrage

bash
cd ms-websocket-jhipster
mvn spring-boot:run
🌐 Accès au Dashboard

Dashboard Principal : http://localhost:8080/test-websocket.html

⚡ ms4-grpc-jhipster/

Service Bancaire gRPC avec Client Python

Service bancaire haute performance utilisant gRPC avec support du streaming.

🛠️ Technologies

Backend: Spring Boot, gRPC, Protobuf, JHipster
Client: Python, gRPC
Communication: gRPC avec streaming bidirectionnel
Sérialisation: Protocol Buffers
💰 Fonctionnalités

👤 Création et gestion de clients bancaires
💰 Consultation de solde en temps réel
🔄 Transferts d'argent sécurisés
📊 Streaming des transactions historiques
🐍 Client Python de test
🚀 API haute performance avec gRPC
🚀 Démarrage

bash
# Serveur Java
cd ms4-grpc-jhipster
mvn spring-boot:run

# Client Python (dans un autre terminal)
cd ms4-grpc-jhipster/python_client
python3 -m venv venv
source venv/bin/activate
pip install grpcio grpcio-tools
python client_banque.py
📡 API gRPC

Le service expose 4 méthodes principales :

CreerClient() - Création de compte
ConsulterSolde() - Consultation de solde
EffectuerTransfert() - Transfert entre comptes
StreamTransactions() - Streaming des transactions
👨‍💻 Auteur

Ben Sultan

📄 Licence

MIT License

💡 Les deux projets sont i
