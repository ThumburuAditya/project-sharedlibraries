def call() {
    pipeline {

        agent {
            node {
                label 'workstation-project'
            }
        }


        stages {
            stage('Code Quality') {
                steps {

                    sh 'echo Code Quality'
                    //sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.9.183:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
                }
            }
            stage('Unit testng') {
                steps {

                    sh 'echo Unit Testing'
                }
            }
            stage('Checkmarx SAST Scan') {
                steps {

                    sh 'echo SAST Scan'
                }
            }
            stage('Checkmarx SCA Scan') {
                steps {

                    sh 'echo SCA Scan'
                }
            }
            stage('Release Application') {
                when {
                    expression {
                        env.TAG_NAME ==~ ".*"
                    }
                }
                steps {

                    sh 'echo Release Application'
                }
            }

        }

        post {
            always {
                cleanWs()
            }
        }

    }
}