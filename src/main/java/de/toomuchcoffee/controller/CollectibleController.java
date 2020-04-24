package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.CollectibleService;
import de.toomuchcoffee.view.CollectibleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

@RestController
@RequestMapping(value = "/collectibles")
@CrossOrigin
@RequiredArgsConstructor
public class CollectibleController {

    private final CollectibleService collectibleService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public CollectibleDto findOne(@PathVariable Long id) {
        return collectibleService.findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CollectibleDto> find(
            @RequestParam(name = "verbatim", required = false) String verbatim,
            @RequestParam(name = "line", required = false) String line) {
        if (!isNullOrEmpty(verbatim) || !isNullOrEmpty(line)) {
            return collectibleService.findByFilter(verbatim, line);
        }
        return collectibleService.find();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Long addCollectible(@RequestBody CollectibleDto collectible) {
        return collectibleService.add(collectible);
    }

    @RequestMapping(value = "tag/{tag}", method = RequestMethod.GET)
    public List<CollectibleDto> findByTag(@PathVariable String tag) {
        return collectibleService.findByTag(tag);
    }

    @ResponseBody
    @RequestMapping(value= "/{collectibleId}/thumb", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getCollectibleThumbnail(@PathVariable Long collectibleId) throws IOException {
        return collectibleService.getCollectibleThumbnail(collectibleId);
    }
}
