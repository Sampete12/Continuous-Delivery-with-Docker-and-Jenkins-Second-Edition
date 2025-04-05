pipeline {
    agent {
        label 'docker-agent'
    }
    
    environment {
        REGISTRY = "https://localhost:5001"
        REGISTRY_HOST = "localhost:5001"
    }
    
    stages {
        
        stage('Run tests and generate reports') {
            steps {
                git url: 'https://github.com/Sampete12/Continuous-Delivery-with-Docker-and-Jenkins-Second-Edition.git', branch: 'main'
            sh '''
             whoami
             sudo chown -R jenkins:jenkins ~/.kube
             ls -la ~/.kube
             cd Chapter08/sample1
             export KUBECONFIG=/root/.kube/config.modified
             kubectl --insecure-skip-tls-verify apply -f calculator.yaml
             kubectl --insecure-skip-tls-verify apply -f hazelcast.yaml

             kubectl --insecure-skip-tls-verify wait --for=condition=ready pod -l app=calculator --timeout=60s
             sleep 30

             echo "$(kubectl get service calculator-service --insecure-skip-tls-verify -o jsonpath='{.spec.clusterIP}') calculator-service" >> /etc/hosts

             curl "calculator-service:8080/sum?a=1&b=2"
             
             kubectl --insecure-skip-tls-verify get pods
             
             kubectl --insecure-skip-tls-verify delete -f calculator.yaml
             kubectl --insecure-skip-tls-verify delete -f hazelcast.yaml
             '''
             
            }
        }
    }
}

