def call() {
    pipeline {

        agent {
            node {
                label 'workstation-project'
            }
        }

        parameters {
            string(name: 'ip_address', defaultValue: '', description: 'ipaddress of the component')
            string(name: 'component', defaultValue: '', description: 'enter the component name')
            //string(name: 'app_version', defaultValue: '', description: 'App Version to deploy')
        }

        stages {
            stage('Ansible') {
                steps {

                    sh 'ansible-playbook -i ${ip_address}, roboshop.yml -e ansible_user=centos -e ansible_password=DevOps321 -e role_name=${component}'
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