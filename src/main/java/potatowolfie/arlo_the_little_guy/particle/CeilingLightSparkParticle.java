package potatowolfie.arlo_the_little_guy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class CeilingLightSparkParticle extends SingleQuadParticle {

    private CeilingLightSparkParticle(ClientLevel level, double x, double y, double z,
                                      double xa, double ya, double za, SpriteSet sprites) {
        super(level, x, y, z, xa, ya, za, sprites.get(level.getRandom()));
        this.hasPhysics = true;
        this.friction = 0.92F;
        this.gravity = 0.05F;
        this.lifetime = 20 + level.getRandom().nextInt(10);
        this.quadSize = 0.3F + level.getRandom().nextFloat() * 0.1F;

        this.xd = xa;
        this.yd = Math.min(0.0D, ya);
        this.zd = za;

        this.rCol = 1.0F;
        this.gCol = 0.8F + level.getRandom().nextFloat() * 0.2F;
        this.bCol = 0.2F + level.getRandom().nextFloat() * 0.2F;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.yd -= this.gravity;
        this.move(this.xd, this.yd, this.zd);

        this.xd *= this.friction;
        this.zd *= this.friction;

        if (this.onGround) {
            this.xd *= 0.3F;
            this.zd *= 0.3F;
            this.yd = 0;
        }

        this.alpha = 1.0F - ((float) this.age / (float) this.lifetime);
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xa, double ya, double za,
                                       RandomSource random) {
            return new CeilingLightSparkParticle(level, x, y, z, xa, ya, za, this.sprites);
        }
    }
}