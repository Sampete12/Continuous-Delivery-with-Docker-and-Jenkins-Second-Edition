pipeline {
    agent any

    environment {
        REGISTRY = "https://localhost:5001"
        REGISTRY_HOST = "localhost:5001"
        PROJECT_DIR = "Chapter08/sample1"
        IMAGE_NAME = ""
        IMAGE_VERSION = ""
    }

    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Sampete12/Continuous-Delivery-with-Docker-and-Jenkins-Second-Edition.git',
                    branch: env.BRANCH_NAME
                sh """
                set -e
                cd Chapter08/sample1
                chmod +x Chapter08/sample1/gradlew
                """
            }
        }

        stage('Run Tests') {
            
            when { branch 'main' }
            steps {
                sh """
                cd Chapter08/sample1
                chmod +x Chapter08/sample1/gradlew
                ./gradlew test
                ./gradlew jacocoTestReport
                ./gradlew jacocoTestCoverageVerification
                ./gradlewcheckstyleTest
                """
 
            }
    
        }
 
        stage('Feature Tests') {
            when { branch 'feature' }
            steps {
                sh """
                cd Chapter08/sample1
                chmod +x Chapter08/sample1/gradlew
                ./gradlew test
                ./gradlew jacocoTestReport
                ./gradlew checkstyleTest 
                """
            }
 
        }
        
        stage('Playground Tests') {
            when { branch 'playground' }
            steps {
                sh """
                cd Chapter08/sample1
                chmod +x Chapter08/sample1/gradlew
                ./gradlew test
                ./gradlew jacocoTestReport
                ./gradlew checkstyleTest 
                """
            }
 
        }

        stage('Publish Reports') {
            steps {
                publishHTML (
                    target: [
                        reportDir: 'Chapter08/sample1/build/reports/tests/test',
                        reportFiles: 'index.html',
                        reportName: "JaCoCo Report"
                    ]
                )
            }
        }

        stage('Build Container') {
            when {
                expression { env.BRANCH_NAME != 'playground' }
            }
            steps {
                script{
                    if (env.BRANCH_NAME == 'main') {
                        env.IMAGE_NAME = "calculator"
                        env.IMAGE_VERSION = "1.0"
                    } else if (env.BRANCH_NAME == 'feature') {
                        env.IMAGE_NAME = "calculator-feature"
                        env.IMAGE_VERSION = "0.1"
                    }

                    sh """
                    set -e
                    cd Chapter08/sample1
                    ./gradlew build              
                    """         
                }
            }
        }

        stage('Login to Registry and build image') {
            when {
                expression { env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'feature' }
            }
            steps {
                script {
                    withCredentials([usernamePassword(crednetialsId: 'docker-registry', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh """
                            set -e
                            cd Chapter08/sample1
                            echo "\$DOCKER_PASS" | docker login \$REGISTRY -u \$DOCKER_USER --password-stdin
                            docker build -t ${IMAGE_NAME} .
                            docker tag ${IMAGE_NAME} ${REGISTRY_HOST}/${IMAGE_NAME}:${IMAGE_TAG}
                            docker push ${REGISTRY_HOST}/${IMAGE_NAME}:${IMAGE_TAG}
                            
                        """
                    }
                
                }    
            }
        }
    }
}
