package com.bb.cs.controller;

import com.bb.cs.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

@RestController
@RequestMapping("/")
@ConfigurationProperties(prefix="info")
public class InfoController {

    @Value("${build.time}")
    private String buildTime;


    /**
     * http://localhost:9090/api/info
     * @return Service properties
     */
    @RequestMapping(method = RequestMethod.GET, value = "/info", produces = "text/plain")
    private String info() {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(getAllProperties());
    }

    private Map<String, Object> getAllProperties() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("ApplicationProperties", getAppProperties());
        map.put("RuntimeProperties", getRuntimeProperties());
        map.put("NetworkProperties", getNetworkProperties());
        map.put("SystemProperties", getSystemProperties());
        return map;
    }

    private Map<String, Object> getAppProperties() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("ApplicationName", "Customer Service (REST in-memory)");
        map.put("AppJar", getAppJar());
        map.put("BuildCreated", buildTime);
        map.put("endpoints", getEndpointsProperties());
        return map;
    }

    private Map<String, Object> getEndpointsProperties() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("info", "http://localhost:9090/info");
        map.put("HTTP GET customers", "http://localhost:9090/api/customers");
        return map;
    }


    private static String getAppJar() {
        try {
            return Application.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        } catch (Throwable e) {
            e.printStackTrace();
            return "Not found";
        }
    }

    private Map<String, Object> getRuntimeProperties() {
        TreeMap<String, Object> map = new TreeMap<>();
        Runtime runtime = Runtime.getRuntime();
        map.put("availableProcessors", runtime.availableProcessors());
        map.put("maxMemoryBytes", runtime.maxMemory());
        map.put("totalMemory", runtime.totalMemory());
        map.put("availableMemoryBytes", runtime.freeMemory());

        return map;
    }

    private Map<String, Object> getSystemProperties() {
        Properties prop = System.getProperties();

        TreeMap<String, Object> map = new TreeMap<>();
        prop.forEach((k,v)->map.put(k.toString(), v));

        return map;
    }

    private Map<String, String> getNetworkProperties() {
        TreeMap<String, String> map = new TreeMap<>();
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            map.put("localhost.IP", localhost.getHostAddress());
            map.put("localhost.CanonicalHostName", localhost.getCanonicalHostName());

            InetAddress[] allIpAddresses = InetAddress.getAllByName(localhost.getCanonicalHostName());
            map.put("localhost.HostAddresses", "[" + Arrays.toString(allIpAddresses) + "]");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return map;
    }
}
