package com.mbenabda.techwatch.testES.config.drools;

import com.mbenabda.techwatch.testES.domain.Genre;
import com.mbenabda.techwatch.testES.domain.Instrument;
import com.mbenabda.techwatch.testES.repository.GenreRepository;
import com.mbenabda.techwatch.testES.repository.InstrumentRepository;

import java.util.Arrays;

public class FakeDataInitializer {
    private final GenreRepository genreRepository;
    private final InstrumentRepository instrumentRepository;

    public FakeDataInitializer(GenreRepository genreRepository, InstrumentRepository instrumentRepository) {
        this.genreRepository = genreRepository;
        this.instrumentRepository = instrumentRepository;
    }

    public void init() {
        /*
            genre popularity over time derived from
            http://public.tableau.com/views/TracingtheLifecycleofEachMusicGenreintheU_S_/GenrePopularity?:embed=y&:loadOrderID=1&:display_count=yes
         */
        genreRepository.save(Arrays.asList(
            genre("hip hop"      , 2002 , 2016),
            genre("pop"          , 1985 , 2016),
            genre("country"      , 1998 , 2016),
            genre("rock & roll"  , 1958 , 1980),
            genre("rock"         , 1962 , 2013),
            genre("r&b"          , 1982 , 2016),
            genre("soul"         , 1958 , 2000),
            genre("folk"         , 1969 , 1984),
            genre("house/trance" , 1990 , 1998),
            genre("jazz"         , 1958 , 1970),
            genre("latin"        , 1998 , 2003),
            genre("blues"        , 1958 , 1977),
            genre("metal"        , 1998 , 2012),
            genre("punk"         , 2002 , 2013),
            genre("ska/raggae"   , 1989 , 1997),
            genre("dancehall"    , 2001 , 2007),
            genre("a capella"    , 1958 , 2016)
        ));

        instrumentRepository.save(Arrays.asList(
            instrument("ACOUSTIC"   , "STRING" , "guitar"   , 1.5f , 80000.0f , 50 , .3f , 2),
            instrument("ELECTRONIC" , "STRING" , "guitar"   , 1.5f , 80000.0f , 50 , .5f , 5),
            instrument("ACOUSTIC"   , "DRUM"   , "triangle" , .5f  , 800.0f   , 12 , 0   , 1),
            instrument("ACOUSTIC"   , "VOICE"  , "beatbox"  , 0    , 0        , 0  , 0   , 1),
            instrument("ACOUSTIC"   , "VOICE"  , "voice"    , 0    , 0        , 0  , .2f , 5)
        ));
    }

    private Instrument instrument(String category, String king, String name, float weight, float volume, float price, float loudness, int requiredHoursOfPracticePerWeek) {
        Instrument instrument = new Instrument();
        instrument.setName(name);
        instrument.setCategory(category);
        instrument.setKind(king);
        instrument.setAverageWeight(weight);
        instrument.setAverageVolume(volume);
        instrument.setAverageLowEndPrice(price);
        instrument.setLoudness(loudness);
        instrument.setRequiredHoursOfPracticePerWeek(requiredHoursOfPracticePerWeek);
        return instrument;
    }

    private Genre genre(String code, int popularitySkpikeStartingYear, int popularitySkpikeEndingYear) {
        Genre genre = new Genre();
        genre.setCode(code);
        genre.setGoldenAgeStartingYear(popularitySkpikeStartingYear);
        genre.setGoldenAgeEndingYear(popularitySkpikeEndingYear);
        return genre;
    }
}
