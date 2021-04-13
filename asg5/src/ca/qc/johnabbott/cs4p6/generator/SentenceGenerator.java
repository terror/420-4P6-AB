package ca.qc.johnabbott.cs4p6.generator;

import java.util.Random;

/**
 * Generate random "sentences".
 */
public class SentenceGenerator implements Generator<String> {

    private WordGenerator generator;
    private int maximumLength;

    public SentenceGenerator(WordGenerator generator, int maximumLength) {
        this.generator = generator;
        this.maximumLength = maximumLength;
    }

    @Override
    public String generate(Random random) {
        StringBuilder builder = new StringBuilder();
        int length = random.nextInt(maximumLength);
        for(int i = 0; i < length; i++) {
            builder.append(generator.generate(random));
            if(i < length - 1)
                builder.append(' ');
        }
        return builder.toString();
    }
}
