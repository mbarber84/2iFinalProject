Overview

This project is an automation testing framework for an e-commerce demo site built using Java, Selenium WebDriver, and JUnit 5. It uses a Page Object Model (POM) structure and includes utilities for handling coupons, CSV data, and screenshots. The framework also integrates with Allure Reports for test reporting.

The framework is designed to test:

Logging in and out

Adding products to the cart

Applying coupon discounts

Completing purchases

Verifying orders in the "My Account" section

Table of Contents

Project Structure

Prerequisites

Setup Instructions

Configuration

Test Data

Running Tests

Project Utilities

Reporting

Key Features

Project Structure
uk.co.twoitesting.twoitesting
│
├─ basetests/
│   └─ BaseTests.java          # Base test class for setup/teardown
│
├─ pomclasses/
│   ├─ BasePOM.java            # Base class for all POMs
│   ├─ LoginPOM.java           # Login page interactions
│   ├─ ShopPOM.java            # Shop page interactions
│   ├─ CartPOM.java            # Cart page interactions
│   ├─ CheckoutPOM.java        # Checkout page interactions
│   ├─ AccountPOM.java         # Account page interactions
│   ├─ OrdersPOM.java          # My Orders page interactions
│   └─ componentPOM/           
│       ├─ NavPOM.java         # Navigation bar interactions
│       └─ PopUpPOM.java       # Popup handling
│
├─ utilities/
│   ├─ ConfigLoader.java       # Loads config.properties
│   ├─ CsvCouponLoader.java    # Loads coupons from CSV
│   ├─ CouponData.java         # Record class for coupon info
│   └─ Helpers.java            # Helper methods for screenshots, scrolling, etc.
│
├─ test/
│   ├─ TestCase1.java          # Parameterized test for product purchase with coupons
│   └─ TestCase2.java          # Full end-to-end purchase test
│
├─ resources/
│   ├─ config.properties       # Base URL, credentials, billing info
│   └─ coupons.csv             # Coupons used in tests
│
└─ pom.xml                     # Maven build configuration (if applicable)

Prerequisites

Ensure you have the following installed:

Java JDK 17 or above

Maven or Gradle (if using for dependency management)

Chrome and/or Firefox browser

WebDriver binaries in system PATH (chromedriver/geckodriver)

IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

Optional: Allure Commandline for report generation

Setup Instructions

Clone the repository:

git clone <repo-url>
cd twoitesting-automation


Ensure the WebDriver for your browser is installed and in your system PATH.

Install dependencies using Maven:

mvn clean install


Update config.properties with your credentials and billing information if needed.

Configuration

The config.properties file contains key test configurations:

# Base URL of the e-commerce site
base.url=https://www.edgewordstraining.co.uk/demo-site

# Login credentials
username=hello@2itesting.co.uk
password=12iTestingProject

# Billing details
billing.firstname=John
billing.lastname=Doe
billing.address=123 Testing Street
billing.city=Liverpool
billing.postcode=L1 2AB
billing.email=hello@2itesting.co.uk
billing.phone=07123456789


The framework supports a system property override for browsers:

-Dbrowser=firefox


Default browser is Chrome.

Test Data

Coupons CSV (coupons.csv):

key,code,discount
coupon.edgewords,Edgewords,0.15
coupon.2idiscount,2idiscount,0.25


This CSV is loaded dynamically by CsvCouponLoader and used in TestCase1 for parameterized tests.

Running Tests
From IDE

Right-click on a test class or method and select Run.

From Maven

Run all tests:

mvn test


Run a specific test by tag (e.g., RunMe):

mvn test -Dgroups=RunMe

Optional: Run with specific browser
mvn test -Dbrowser=firefox

Project Utilities

ConfigLoader: Reads config.properties

CsvCouponLoader: Reads coupons from CSV into CouponData objects

Helpers:

Take screenshots and attach to Allure

Scroll elements into view

Convert price strings to BigDecimal

Reporting

The project integrates Allure Reports:

Run tests:

mvn test


Generate and view report:

allure serve target/allure-results


Screenshots and test steps are automatically attached.

Key Features

Page Object Model for maintainable code

Parameterized Tests for multiple coupon/product combinations

Full End-to-End Purchase Tests including order verification

CSV-Driven Coupons for easy test data management

Allure Reporting with screenshots

Browser Flexibility (Chrome/Firefox)

Automatic Cart Cleanup after each test

Notes

Cart is automatically emptied after each test to ensure test isolation.

Screenshots are saved under target/screenshots with timestamps.

Discount calculations in tests use BigDecimal for accuracy.
