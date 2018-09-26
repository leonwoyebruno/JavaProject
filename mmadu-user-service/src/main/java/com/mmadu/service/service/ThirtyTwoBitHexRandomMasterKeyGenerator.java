package com.mmadu.service.service;

import java.util.Random;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

@Component
public class ThirtyTwoBitHexRandomMasterKeyGenerator implements MasterKeyGenerator {

    private long seed;
    private Random random;

    public void setSeed(long seed) {
        this.seed = seed;
    }

    @PostConstruct
    public void initialize(){
        random = new Random(seed);
    }

    @Override
    public String generateMasterKey() {
        if(random == null){
            throw new IllegalStateException("Initialize generator first");
        }
        byte [] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        return new String(Hex.encode(randomBytes));
    }
}
