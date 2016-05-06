import java.util.Properties
import java.lang.System
import jenkins.model.*
import hudson.security.*
import org.jenkinsci.plugins.*

println "--> LDAPsecurity: Read properties from the file"

def home_dir = System.getenv("JENKINS_HOME")
GroovyShell shell = new GroovyShell()
def helpers = shell.parse(new File("$home_dir/init.groovy.d/helpers.groovy"))
Properties properties = helpers.readProperties("$home_dir/jenkins.properties")

println "--> Configure LDAP"

if(properties.isLDAP.toBoolean()) {
    SecurityRealm ldap_realm = new LDAPSecurityRealm(properties.ldapServer,
                                                     properties.ldapRootDN,
                                                     properties.ldapUserSearchBase,
                                                     properties.ldapUserSearch,
                                                     properties.ldapGroupSearchBase,
                                                     properties.ldapManagerDN,
                                                     properties.ldapManagerPassword,
                                                     properties.ldapInhibitInferRootDN.toBoolean())
    Jenkins.instance.setSecurityRealm(ldap_realm)
    Jenkins.instance.save()
}