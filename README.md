# Secure Banking System

Technology Stack: **Java, SpringMVC, MySQL**

## About

A secure banking system (SBS) is a software system developed primarily to facilitate secure banking transactions and user account management through the Internet. A banking organization often needs to track various operations performed by both the internal and external users using the organization’s banking infrastructure. The focus of this project is to develop a SBS to facilitate secure banking transactions and account management required by any banking organization.

## Installation

1. Clone the repo to your machine.
2. Import as a Maven project in Eclipse.
3. Run the database script
4. Update you database config in the file: `database.properties`
5. Update your smtp server details (to send OTP) in the file: `smtp.properties`
6. Add your keystore file which stores your certificate and private key: `keystore.jks`
7. Build project with maven goals: `clean install`
8. Install a server like `Tomcat`
9. Run the application on the server.

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D
