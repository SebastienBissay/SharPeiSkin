import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class SharPeiSkin extends PApplet {
    public static void main(String[] args) {
        PApplet.main(SharPeiSkin.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
        noiseSeed(floor(random(MAX_INT)));
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        stroke(STROKE_COLOR.red(), STROKE_COLOR.green(), STROKE_COLOR.blue(), STROKE_COLOR.alpha());
        noFill();
        noLoop();
    }

    @Override
    public void draw() {
        renderRectangle(new PVector(MARGIN, MARGIN), new PVector(WIDTH - MARGIN, HEIGHT - MARGIN));
        saveSketch(this);
    }

    private void renderRectangle(PVector NE, PVector SW) {
        float area = abs(SW.x - NE.x) * abs(SW.y - NE.y);
        for (int i = 0; i < 10 * area; i++) {
            float x, y;
            do {
                float radius = random(0, sqrt(sq(SW.x - NE.x) + sq(SW.y - NE.y)) + 2 * i / area);
                float angle = random(0, PI / 2);
                x = min(NE.x, SW.x) + radius * cos(angle);
                y = min(SW.y, NE.y) + radius * sin(angle);
            }
            while (x < min(NE.x, SW.x) - random(2 * i / area)
                    || x > max(NE.x, SW.x) + random(2 * i / area)
                    | y < min(SW.y, NE.y) - random(2 * i / area)
                    || y > max(SW.y, NE.y) + random(2 * i / area));
            float noise = NOISE_FACTOR * pow(noise(x * NOISE_SCALE, y * NOISE_SCALE), NOISE_EXPONENT);
            x += cos(2 * TWO_PI * noise) * noise;
            y += sin(2 * TWO_PI * noise) * noise;
            point(x, y);
        }
    }
}
