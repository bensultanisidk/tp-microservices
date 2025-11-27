import grpc
import banque_pb2
import banque_pb2_grpc

def main():
    print("🚀 Test du service gRPC Banque")
    print("Connexion au serveur sur localhost:9090...")
    
    # Connexion au serveur Java
    channel = grpc.insecure_channel('localhost:9090')
    stub = banque_pb2_grpc.BanqueServiceStub(channel)
    
    try:
        # Test 1: Créer un client
        print("\n1. Création d'un client...")
        reponse_client = stub.CreerClient(
            banque_pb2.ClientRequest(
                numero_tel="+212612345678",
                solde_initial=100000
            )
        )
        print(f"✅ Client créé: {reponse_client.message}")
        print(f"   📱 Numéro: {reponse_client.numero_tel}")
        print(f"   💰 Solde: {reponse_client.solde_actuel}")
        
        # Test 2: Consulter le solde
        print("\n2. Consultation du solde...")
        reponse_solde = stub.ConsulterSolde(
            banque_pb2.SoldeRequest(numero_tel="+212612345678")
        )
        print(f"✅ Solde consulté: {reponse_solde.solde}")
        print(f"   📅 Date: {reponse_solde.date_consultation}")
        
        # Test 3: Faire un transfert
        print("\n3. Transfert entre clients...")
        reponse_transfert = stub.EffectuerTransfert(
            banque_pb2.TransfertRequest(
                emetteur="+212612345678",
                destinataire="+212698765432", 
                montant=5000
            )
        )
        print(f"✅ Transfert: {reponse_transfert.message}")
        print(f"   📋 Référence: {reponse_transfert.reference}")
        print(f"   💰 Nouveau solde: {reponse_transfert.nouveau_solde_emetteur}")
        
        # Test 4: Voir les transactions (streaming)
        print("\n4. Streaming des transactions...")
        print("   Récupération de 3 transactions...")
        reponse_stream = stub.StreamTransactions(
            banque_pb2.StreamRequest(
                numero_tel="+212612345678",
                max_transactions=3
            )
        )
        
        for i, transaction in enumerate(reponse_stream):
            print(f"   📈 Transaction {i+1}:")
            print(f"      🆔 ID: {transaction.id}")
            print(f"      📝 Type: {transaction.type}")
            print(f"      💰 Montant: {transaction.montant}")
            print(f"      📅 Date: {transaction.date}")
            print(f"      📋 Description: {transaction.description}")
        
        print("\n🎉 Tous les tests sont réussis!")
        
    except Exception as e:
        print(f"❌ Erreur: {e}")

if __name__ == '__main__':
    main()