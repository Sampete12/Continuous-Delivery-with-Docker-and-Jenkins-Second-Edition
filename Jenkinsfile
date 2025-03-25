pipeline {
    agent{
        docker {
            image 'dlambrig/gradle-agent:latest'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
            alwaysPull true
            customWorkspace '/home/Jenkins/.gradle/workspace'
        }
    }

    environment {
        REGISTRY = "https://localhost:5001" // Replace with actual registry address
        REGISTRY_HOST = "localhost:5001" // Replace with actual registry address
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
                    branch: 'main'
                sh "chmod +x Chapter08/sample1/gradlew"
            }
        }

        stage('Run Tests') {
            
            when { branch 'master' }
            steps {
                sh """
                cd Chapter08/sample1
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
                if (env.BRANCH_NAME == 'main') {
                    env.IMAGE_NAME = "calculator"
                    env.IMAGE_VERSION = "1.0"
                } else if (env.BRANCH_NAME == 'feature') {
                    env.IMAGE_NAME = "calculator-feature"
                    env.IMAGE_VERSION = "0.1"
                }

                sh """
                set -e
                cd $Chapter08/sample1
                ./gradlew build
                docker build -t ${IMAGE_NAME}:${IMAGE_VERSION}              
                """         
                
            }
        }

        stage('Push Container') {
            when {
                expression { env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'feature' }
            }
            steps {
                sh """
                docker tag ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_VERSION} ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_VERSION}
                docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_VERSION}
                """
                
            }
        }
    }
}
