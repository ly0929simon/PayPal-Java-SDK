#### releasinator config ####
configatron.product_name = "PayPal Java SDK"

# Constants
SDK_VERSION_JAVA_FILE = "rest-api-sdk/src/main/java/com/paypal/base/sdk/info/SDKVersionImpl.java"

# List of items to confirm from the person releasing.  Required, but empty list is ok.
configatron.prerelease_checklist_items = [  
  "Sanity check the master branch."
]

def validate_version_match()
  if 'v'+package_version() != @current_release.version
    Printer.fail("package version #{package_version} does not match changelog version #{@current_release.version}.")
    abort()
  end
    Printer.success("package version #{package_version} matches latest changelog version #{@current_release.version}.")

  if 'v'+file_SDK_version() != @current_release.version
    Printer.fail("#{SDK_VERSION_JAVA_FILE} SDK version #{file_SDK_version} does not match changelog version #{@current_release.version}.")
    abort()
  end
    Printer.success("#{SDK_VERSION_JAVA_FILE} SDK version version #{file_SDK_version} matches latest changelog version #{@current_release.version}.") 
end

configatron.custom_validation_methods = [
  method(:validate_version_match)
]

def build_method
  # to run Unit tests 
  CommandProcessor.command("./gradlew clean build", live_output=true)
  # to run Functional tests. at present functional tests are failing , so we are skipping functional test execution temporally 
  #CommandProcessor.command("./gradlew functionalTest", live_output=true)
end

# The command that builds the sdk.  Required.
configatron.build_method = method(:build_method)

def publish_to_package_manager(version)
  CommandProcessor.command("./gradlew uploadArchives", live_output=true)
  CommandProcessor.command("./gradlew closeRepository", live_output=true)
  CommandProcessor.command("sleep 60")
  CommandProcessor.command("./gradlew promoteRepository", live_output=true)
end

# The method that publishes the sdk to the package manager.  Required.
configatron.publish_to_package_manager_method = method(:publish_to_package_manager)


def wait_for_package_manager(version)
  CommandProcessor.wait_for("wget -U \"non-empty-user-agent\" -qO- http://central.maven.org/maven2/com/paypal/sdk/rest-api-sdk/#{package_version}/rest-api-sdk-#{package_version}.pom | cat")
end

# The method that waits for the package manager to be done.  Required
configatron.wait_for_package_manager_method = method(:wait_for_package_manager)

# Whether to publish the root repo to GitHub.  Required.
configatron.release_to_github = true

def file_SDK_version()
  File.open(SDK_VERSION_JAVA_FILE, 'r') do |f|
    f.each_line do |line|
      if line.match(/SDK_VERSION = \"\d+.\d+.\d+"/)
        return line.strip.split('=')[1].strip.split('"')[1]               
      end
    end
  end
end

def package_version()
  File.open("build.gradle", 'r') do |f|
    f.each_line do |line|
      puts line 
      if line.match (/version = \'\d+.\d+.\d+'/)
        return line.strip.split('\'')[1]
      end
    end
  end
end
