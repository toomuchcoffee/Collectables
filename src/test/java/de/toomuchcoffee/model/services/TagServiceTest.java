package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {

    @InjectMocks
    private TagService tagService = new TagService();

    @Test
    public void testGetVerbatimTags() throws Exception {
        Pair<Set<String>, Set<String>> chewbacca = tagService.getVerbatimTags("Chewbacca");
        assertEquals(
                Pair.of(Sets.newHashSet("chewbacca"), Sets.newHashSet()),
                chewbacca);

        Pair<Set<String>, Set<String>> obiWanKenobi = tagService.getVerbatimTags("Obi-Wan Kenobi");
        assertEquals(
                Pair.of(Sets.newHashSet("obiwan kenobi", "obiwankenobi"), Sets.newHashSet()),
                obiWanKenobi);

        Pair<Set<String>, Set<String>> hanSoloHotOutfit = tagService.getVerbatimTags("Han Solo (Hoth Outfit)");
        assertEquals(
                Pair.of(Sets.newHashSet("han solo", "hansolo"), Sets.newHashSet("hoth outfit", "hothoutfit")),
                hanSoloHotOutfit);

        Pair<Set<String>, Set<String>> cloudCarPilot = tagService.getVerbatimTags("(Twin Pod) Cloud Car Pilot");
        assertEquals(
                Pair.of(Sets.newHashSet("cloud car pilot", "cloudcarpilot"), Sets.newHashSet("twin pod", "twinpod")),
                cloudCarPilot);

        Pair<Set<String>, Set<String>> artooDetoo = tagService.getVerbatimTags("Artoo Detoo (R2-D2) (with Sensorscope)");
        assertEquals(
                Pair.of(Sets.newHashSet("artoo detoo", "artoodetoo"), Sets.newHashSet("r2d2", "withsensorscope", "with sensorscope")),
                artooDetoo);
    }

}