pipeline {
    
    agent {
        label 'docker-agent'
    }
    
    triggers {
        pollSCM('* * * * *')
        
    }
    
    environment {
        REGISTRY = "https://localhost:5001"
        REGISTRY_HOST = "localhost:5001"
        BUILD_TIMESTAMP = "${new Date().format('yyyyMMddHHmmss')}"
    }
    
    stages {
        stage("Compile") { 
            steps { 
                git url: 'https://github.com/Sampete12/Continuous-Delivery-with-Docker-and-Jenkins-Second-Edition.git', branch: 'main'
                sh """
                pwd
                cd Chapter08/sample1
                chmod +x gradlew
                bash gradlew compileJava
                """
                }
        }
                
        stage("tests") {
            steps {
                sh """
                cd Chapter08/sample1
                chmod +x gradlew
                bash gradlew test
                bash gradlew jacocoTestReport
                """
            } 
            
        }
        
        stage("Build") { 
            steps { 
                sh """
                pwd
                cd Chapter08/sample1
                chmod +x gradlew
                bash gradlew build
                """ 
                
            } 
            
        }
        
        stage("login to Registry and build image and push") { 
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-registry', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh """
                        set -e
                        cd Chapter08/sample1
                        echo "\$DOCKER_PASS" | docker login \$REGISTRY -u \$DOCKER_USER --password-stdin
                        docker build -t samuelpete1/calculator:${BUILD_TIMESTAMP} .
                        docker tag samuelpete1/calculator:${BUILD_TIMESTAMP} ${REGISTRY_HOST}/samuelpete1/calculator:${BUILD_TIMESTAMP}
                        docker push ${REGISTRY_HOST}/samuelpete1/calculator:${BUILD_TIMESTAMP}
                        """
                    }
                }
                
                } 
            
        }
        
        stage ("Update version") {
            steps {
                sh """
                cd Chapter08/sample1
                sed -i 's/{{VERSION}}/${BUILD_TIMESTAMP}/g' calculator.yaml
                """
            }
        }
        stage("Deploy to staging") { 
            steps {
                sh """
                pwd
                """
                sh """
                cd Chapter08/sample1
                ls
                kubectl config use-context docker-desktop
                kubectl config current-context
                export KUBECONFIG=/root/.kube/config.modified
                kubectl --insecure-skip-tls-verify apply -f hazelcast.yaml
                kubectl --insecure-skip-tls-verify apply -f calculator.yaml
                kubectl --insecure-skip-tls-verify get pod
                """
            } 
            
        }
        

        // Performance test stages
        stage("Release") { 
            steps {
                sh """
                pwd
                """
                sh """
                cd Chapter08/sample1
                ls
                export KUBECONFIG=/root/.kube/config.modified
                kubectl config use-context gke_devops1-456821_us-central1_hello-cluster
                kubectl apply -f hazelcast.yaml
                kubectl apply -f calculator.yaml
                """
            }
            
        }
        
        
    }
}
