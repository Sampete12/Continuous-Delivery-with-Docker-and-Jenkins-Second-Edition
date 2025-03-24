pipeline {
    agent any
    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Sampete12/Continuous-Delivery-with-Docker-and-Jenkins-Second-Edition.git',
                    branch: env.BRANCH_NAME
                echo "Running pipeline on branch: ${env.BRANCH_NAME}"
                sh "chmod +x Chapter08/sample1/gradlew"
            }
        }
        
        stage('Run Tests') {
            steps {
                script {
                    def testResult = 0
                    if (env.BRANCH_NAME == 'main') {
                        echo "Running all tests including CodeCoverage..."
                        testResult = sh(script: """
                            cd Chapter08/sample1
                            ./gradlew test || exit 1
                            ./gradlew jacocoTestReport || exit 1
                        """, returnStatus: true)
                    } else {
                        echo "Running all tests EXCEPT CodeCoverage..."
                        testResult = sh(script: """
                            cd Chapter08/sample1
                            ./gradlew test || exit 1
                            ./gradlew integrationTest || exit 1
                            ./gradlew checkstyle || exit 1
                        """, returnStatus: true)
                    }

                    // Generate JaCoCo report no matter what
                    sh "cd Chapter08/sample1 && ./gradlew jacocoTestReport || true"

                    if (testResult == 0) {
                        echo "✅ Tests pass!"
                    } else {
                        echo "❌ Tests fail!"
                    }
                }
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
    }
}
