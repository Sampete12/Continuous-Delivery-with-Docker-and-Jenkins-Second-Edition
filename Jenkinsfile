pipeline {
    agent any
    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Sampete12/Continuous-Delivery-with-Docker-and-Jenkins-Second-Edition.git',
                    branch: env.BRANCH_NAME
                echo "Running pipeline on branch: ${env.BRANCH_NAME}"
            }
        }
        
        stage('Run Tests') {
            steps {
                script {
                    if (env.BRANCH_NAME == 'main') {
                        echo "Running all tests including CodeCoverage"
                        sh """
                        cd Chapter08/sample1
                        ./gradlew test
                        ./gradlew jacocoTestReport
                        """
                        publishHTML (
                            target: [
                                reportDir: 'Chapter08/sample1/build/reports/tests/test',
                                reportFiles: 'index.html',
                                reportName: "JaCoCo Report"
                            ]
                        )
                    } else if (env.BRANCH_NAME.contains('feature')) {
                        echo "Running all tests EXCEPT CodeCoverage"
                        sh """
                        cd Chapter08/sample1
                        ./gradlew test
                        """
                    } else {
                        error "Invalid branch name: ${env.BRANCH_NAME}. Pipeline will fail."
                    }
                }
            }
        }
    }
}
