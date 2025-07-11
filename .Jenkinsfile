node {
    stage('Checkout') {
        echo "📥 Cloning Git repository..."
        git branch: 'main', url: 'https://github.com/CodeAutomate/seleniumAutomation'
    }

    stage('Copy Credentials') {
        echo "🔑 Kopiere Credentials aus Jenkins..."
        bat 'copy "%MY_APP_CREDENTIALS%" src\\test\\resources\\config.properties'
    }

    stage('Setup Maven') {
        echo "⚙️ Setting up Maven environment..."
        def mvnHome = tool name: 'Maven_3.9', type: 'hudson.tasks.Maven$MavenInstallation'
        env.PATH = "${mvnHome}\\bin;${env.PATH}"
    }

    stage('Build') {
        echo "🔨 Building the project..."
        bat 'mvn clean compile'
    }

    stage('Test') {
        echo "🧪 Running Selenium JUnit5 tests..."
        bat 'mvn test'
    }

    stage('Allure Report') {
        echo "📊 Generating Allure report..."
        bat 'mvn allure:report'
        // optional: publish the report (requires Allure plugin on Jenkins)
        allure includeProperties: false, jdk: '', reportBuildPolicy: 'ALWAYS'
    }

    stage('Archive Test Results') {
        echo "🗄️ Archiving surefire reports and JARs..."
        junit 'target/surefire-reports/*.xml'
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
    }

    stage('Cleanup') {
        echo "🧹 Cleaning up workspace..."
        cleanWs()
    }
}