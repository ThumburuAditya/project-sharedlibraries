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

        }

        post {
            always {
                cleanWs()
            }
        }

    }
}