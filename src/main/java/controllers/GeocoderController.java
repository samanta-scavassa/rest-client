package controllers;

import com.trrestclient2.entities.Site;
import com.trrestclient2.repositories.SiteRepository;
import com.trrestclient2.services.GeocoderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GeocoderController {
    private GeocoderService service;
    private SiteRepository repository;

    public GeocoderController(GeocoderService service, SiteRepository repository){
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/geo")
    public Site getLocation(@RequestParam(name = "city", required = false) String city,
                            @RequestParam(name = "state", required = false) String state) {
        String c = city;
        String s = state;

        if(city == null || city.length() == 0) c = "Boston";
        if(state == null || state.length() == 0) s = "MA";
        return (Site) service.getLatLng( c, s);
    }

    @GetMapping("/geo/{id}")
    public Site getLocation(@PathVariable Integer id){
        return repository.findOne(id);
    }

    @GetMapping("/all")
    public List<Site> getAllLocation(){
        return repository.findAll();
    }

    @PostMapping("/geo")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestParam(name = "city") String city,
                     @RequestParam(name = "state") String state){
        repository.save(getLocation(city, state));
    }
}
