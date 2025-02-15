-include= ~${workspace}/cnf/resources/bnd/feature.props
symbolicName=io.openliberty.ejbSecurity-2.0
IBM-App-ForceRestart: install, \
 uninstall
IBM-Provision-Capability: osgi.identity; filter:="(&(type=osgi.subsystem.feature)(osgi.identity=io.openliberty.ejbCore-2.0))", \
 osgi.identity; filter:="(&(type=osgi.subsystem.feature)(|(osgi.identity=io.openliberty.appSecurity-4.0)(osgi.identity=io.openliberty.appSecurity-5.0)(osgi.identity=io.openliberty.appSecurity-6.0)(osgi.identity=io.openliberty.mpJwt-2.1)))"
IBM-Install-Policy: when-satisfied
-features=com.ibm.websphere.appserver.containerServices-1.0
-bundles=com.ibm.ws.security.appbnd, \
 io.openliberty.ejbcontainer.security.internal
kind=ga
edition=core
