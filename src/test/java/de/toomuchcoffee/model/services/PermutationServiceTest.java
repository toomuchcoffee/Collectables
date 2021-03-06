package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PermutationServiceTest {

    @InjectMocks
    private PermutationService permutationService = new PermutationService();

    @Test
    public void testGetVerbatimPermutations() throws Exception {
        Set<String> permutations = permutationService.getPermutations("Imperial TIE Fighter Pilot");
        assertEquals(Sets.newHashSet(
                "imperial tie fighter pilot",
                "imperial tie fighter",
                         "tie fighter pilot",
                "imperial tie",
                         "tie fighter",
                             "fighter pilot",
                "imperial",
                         "tie",
                             "fighter",
                                     "pilot",
                "imperialtiefighterpilot",
                "imperialtiefighter",
                        "tiefighterpilot",
                "imperialtie",
                        "tiefighter",
                           "fighterpilot",
                "imperial",
                        "tie",
                           "fighter",
                                  "pilot"
        ), permutations);
    }

    @Test
    public void testGetVerbatimPermutationsWithParenthesis() throws Exception {
        Set<String> permutations = permutationService.getPermutations("(Twin Pod) Cloud Car Pilot");
        assertTrue(permutations.containsAll(Sets.newHashSet("cloudcar", "cloudcarpilot")));
    }

}