package com.github.lsjunior.sbk8stest.web.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.lsjunior.sbk8stest.jpa.entity.Person;
import com.github.lsjunior.sbk8stest.service.PersonService;
import com.github.lsjunior.sbk8stest.web.utils.ResponseUtils;

@RestController
@RequestMapping(path = "/person")
public class PersonResource {

  private PersonService personService;

  public PersonResource() {
    super();
  }

  @Autowired
  public void setPersonService(PersonService personService) {
    this.personService = personService;
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable("id") final Long id) {
    Person o = this.personService.get(id);
    return ResponseUtils.okOrNotFound(o);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> save(@RequestBody final Person item) {
    Person o = this.personService.save(item);
    return ResponseUtils.okOrNotFound(o);
  }

  @RequestMapping(path = "/{id}", method = {RequestMethod.PUT, RequestMethod.PUT}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> update(@PathVariable("id") final Long id, @RequestBody final Person item) {
    item.setId(id);
    Person o = this.personService.update(item);
    return ResponseUtils.okOrNotFound(o);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list(@RequestParam(name = "q", required = false) final String query, final Pageable pageable) {
    Page<Person> o = this.personService.list(query, pageable);
    return ResponseUtils.okOrNotFound(o);
  }

}
