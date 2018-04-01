package fr.codechill.spring.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;

import fr.codechill.spring.model.Docker;
import fr.codechill.spring.repository.DockerRepository;

public class DockerController {

    private final DockerRepository drepo;
    private final String BASE_URL = "http://localhost:2375";

    public DockerController(DockerRepository drepo) {
        this.drepo = drepo;
    }

    public Docker createDocker() {
        String dockerCreatetUrl = BASE_URL + "/containers/create";
        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("Image", "codechill/ubuntu-base-user");
        body.put("Hostname", "chill");
        body.put("tty", true);
        body.put("OpenStdin", true);
        body.put("AttachStdin", true);
        body.put("StdinOnce", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);

        ResponseEntity<String> res = restTemplate.exchange(dockerCreatetUrl, HttpMethod.POST, entity, String.class);
        Docker docker;
        try {
            JsonNode id = mapper.readValue(res.getBody(), JsonNode.class);
            docker = new Docker(id.get("Id").textValue());
            this.drepo.save(docker);
        } catch (IOException e) {
            docker = null;
        }
        return docker;
    }

}