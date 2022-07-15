package com.github.lsjunior.sbk8stest.web.resources;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.lsjunior.sbk8stest.Log;
import com.google.common.base.Strings;
import com.google.common.io.Resources;
import com.google.common.net.InetAddresses;

@RestController
@RequestMapping(path = "/info")
public class InfoResource implements InitializingBean {

  private static final String COUNT_ATTR = "InfoResource.Session.COUNT";

  private String version;

  private String date;

  private String hostName;

  private List<String> hostAddress;

  public InfoResource() {
    super();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.doLoadVersion();
    this.doLoadHostname();
  }

  private void doLoadVersion() {
    try {
      URL version = Resources.getResource("version.properties");
      Properties properties = new Properties();
      properties.load(version.openStream());

      this.version = properties.getProperty("app.version", "V???");
      this.date = properties.getProperty("app.date", "V???");
    } catch (Exception e) {
      Log.getLog().error(e.getMessage(), e);
    }
  }

  private void doLoadHostname() {
    try {
      this.hostName = System.getenv("HOSTNAME");
      if (Strings.isNullOrEmpty(this.hostName)) {
        this.hostName = InetAddress.getLocalHost().getHostName();
      }

      this.hostAddress = new ArrayList<>();
      Enumeration<NetworkInterface> niEnumeration = NetworkInterface.getNetworkInterfaces();
      while (niEnumeration.hasMoreElements()) {
        NetworkInterface ni = niEnumeration.nextElement();
        Enumeration<InetAddress> iaEnumeration = ni.getInetAddresses();
        while (iaEnumeration.hasMoreElements()) {
          InetAddress ia = iaEnumeration.nextElement();
          if (InetAddresses.isInetAddress(ia.getHostAddress())) {
            this.hostAddress.add(ia.getHostAddress());
          }
        }
      }
    } catch (Exception e) {
      Log.getLog().error(e.getMessage(), e);
    }
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<?> session(final HttpServletRequest request) {
    HttpSession session = request.getSession();
    String sessionId = session.getId();
    Integer count = (Integer) session.getAttribute(InfoResource.COUNT_ATTR);

    if (count == null) {
      count = Integer.valueOf(0);
    }
    count = Integer.valueOf(count.intValue() + 1);
    session.setAttribute(InfoResource.COUNT_ATTR, count);

    Map<String, Object> map = new HashMap<>();
    map.put("version", this.version);
    map.put("date", this.date);
    map.put("sessionId", sessionId);
    map.put("count", count);
    map.put("hostName", this.hostName);
    map.put("hostAddress", this.hostAddress);
    return ResponseEntity.ok(map);
  }
}
