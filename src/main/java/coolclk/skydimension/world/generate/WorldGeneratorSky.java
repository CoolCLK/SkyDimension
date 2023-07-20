package coolclk.skydimension.world.generate;

import coolclk.skydimension.world.chunk.ChunkProviderSky;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

import java.util.Random;

public class WorldGeneratorSky extends WorldGenerator {
    public WorldGeneratorSky() {
        super(true);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        try {
            int x = position.getX();
            int z = position.getZ();

            BlockSand.fallInstantly = true;
            int k = x * 16;
            int l = z * 16;
            Biome biome = worldIn.getBiome(new BlockPos(k + 16, 0, l + 16));
            rand.setSeed(worldIn.getSeed());
            long l1 = (rand.nextLong() / 2L) * 2L + 1L;
            long l2 = (rand.nextLong() / 2L) * 2L + 1L;
            rand.setSeed((long) x * l1 + (long) z * l2 ^ worldIn.getSeed());
            double d;
            if (rand.nextInt(4) == 0) {
                int i1 = k + rand.nextInt(16) + 8;
                int l4 = rand.nextInt(128);
                int i8 = l + rand.nextInt(16) + 8;
                (new WorldGenLakes(Blocks.WATER)).generate(worldIn, rand, new BlockPos(i1, l4, i8));
            }
            if (rand.nextInt(8) == 0) {
                int j1 = k + rand.nextInt(16) + 8;
                int i5 = rand.nextInt(rand.nextInt(120) + 8);
                int j8 = l + rand.nextInt(16) + 8;
                if (i5 < 64 || rand.nextInt(10) == 0) {
                    (new WorldGenLakes(Blocks.LAVA)).generate(worldIn, rand, new BlockPos(j1, i5, j8));
                }
            }
            for (int k1 = 0; k1 < 8; k1++) {
                int j5 = k + rand.nextInt(16) + 8;
                int k8 = rand.nextInt(128);
                int i13 = l + rand.nextInt(16) + 8;
                (new WorldGenDungeons()).generate(worldIn, rand, new BlockPos(j5, k8, i13));
            }

            for (int i2 = 0; i2 < 10; i2++) {
                int k5 = k + rand.nextInt(16);
                int l8 = rand.nextInt(128);
                int j13 = l + rand.nextInt(16);
                (new WorldGenClay(32)).generate(worldIn, rand, new BlockPos(k5, l8, j13));
            }
            for (int j2 = 0; j2 < 20; j2++) {
                int l5 = k + rand.nextInt(16);
                int i9 = rand.nextInt(128);
                int k13 = l + rand.nextInt(16);
                (new WorldGenMinable(Blocks.DIRT.getDefaultState(), 32)).generate(worldIn, rand, new BlockPos(l5, i9, k13));
            }
            for (int k2 = 0; k2 < 10; k2++) {
                int i6 = k + rand.nextInt(16);
                int j9 = rand.nextInt(128);
                int l13 = l + rand.nextInt(16);
                (new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), 32)).generate(worldIn, rand, new BlockPos(i6, j9, l13));
            }

            for (int i3 = 0; i3 < 20; i3++) {
                int j6 = k + rand.nextInt(16);
                int k9 = rand.nextInt(128);
                int i14 = l + rand.nextInt(16);
                (new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 16)).generate(worldIn, rand, new BlockPos(j6, k9, i14));
            }
            for (int j3 = 0; j3 < 20; j3++) {
                int k6 = k + rand.nextInt(16);
                int l9 = rand.nextInt(64);
                int j14 = l + rand.nextInt(16);
                (new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 8)).generate(worldIn, rand, new BlockPos(k6, l9, j14));
            }
            for (int k3 = 0; k3 < 2; k3++) {
                int l6 = k + rand.nextInt(16);
                int i10 = rand.nextInt(32);
                int k14 = l + rand.nextInt(16);
                (new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), 8)).generate(worldIn, rand, new BlockPos(l6, i10, k14));
            }
            for (int l3 = 0; l3 < 8; l3++) {
                int i7 = k + rand.nextInt(16);
                int j10 = rand.nextInt(16);
                int l14 = l + rand.nextInt(16);
                (new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 7)).generate(worldIn, rand, new BlockPos(i7, j10, l14));
            }
            for (int i4 = 0; i4 < 1; i4++) {
                int j7 = k + rand.nextInt(16);
                int k10 = rand.nextInt(16);
                int i15 = l + rand.nextInt(16);
                (new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 7)).generate(worldIn, rand, new BlockPos(j7, k10, i15));
            }
            for (int j4 = 0; j4 < 1; j4++) {
                int k7 = k + rand.nextInt(16);
                int l10 = rand.nextInt(16) + rand.nextInt(16);
                int j15 = l + rand.nextInt(16);
                (new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 6)).generate(worldIn, rand, new BlockPos(k7, l10, j15));
            }
            d = 0.5D;

            NoiseGeneratorPerlin[] generatorCollection = new NoiseGeneratorPerlin[8];
            for (int j = 0; j < 8; j++) {
                generatorCollection[j] = new NoiseGeneratorPerlin(((ChunkProviderSky) DimensionSky.getWorld().getChunkProvider()).seedRandomizer, 0);
            }
            double k4_d2 = 0.0D;
            double k4_d3 = 1.0D;
            for (int i = 0; i < 8; i++) {
                k4_d2 += generatorCollection[i].getValue(((double) k * d) * k4_d3, ((double) l * d) * k4_d3) / k4_d3;
                k4_d3 /= 2D;
            }
            int k4 = (int) ((k4_d2 / 8D + rand.nextDouble() * 4D + 4D) / 3D);

            int l7 = 0;
            if (rand.nextInt(10) == 0) {
                l7++;
            }
            if (biome == Biomes.FOREST) {
                l7 += k4 + 5;
            }
            if (biome == Biomes.TAIGA) {
                l7 += k4 + 5;
            }
            if (biome == Biomes.DESERT) {
                l7 -= 20;
            }
            if (biome == Biomes.ICE_PLAINS) {
                l7 -= 20;
            }
            if (biome == Biomes.PLAINS) {
                l7 -= 20;
            }
            for (int i11 = 0; i11 < l7; i11++) {
                int k15 = k + rand.nextInt(16) + 8;
                int j18 = l + rand.nextInt(16) + 8;
                WorldGenerator worldgenerator = biome.getRandomTreeFeature(rand);
                worldgenerator.generate(worldIn, rand, new BlockPos(k15, worldIn.getHeight(), j18));
            }
            for (int j11 = 0; j11 < 2; j11++) {
                int l15 = k + rand.nextInt(16) + 8;
                int k18 = rand.nextInt(128);
                int i21 = l + rand.nextInt(16) + 8;
                (new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlower.EnumFlowerType.POPPY)).generate(worldIn, rand, new BlockPos(l15, k18, i21));
            }

            if (rand.nextInt(2) == 0) {
                int k11 = k + rand.nextInt(16) + 8;
                int i16 = rand.nextInt(128);
                int l18 = l + rand.nextInt(16) + 8;
                (new WorldGenFlowers(Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.POPPY)).generate(worldIn, rand, new BlockPos(k11, i16, l18));
            }
            if (rand.nextInt(4) == 0) {
                int l11 = k + rand.nextInt(16) + 8;
                int j16 = rand.nextInt(128);
                int i19 = l + rand.nextInt(16) + 8;
                (new WorldGenBush(Blocks.BROWN_MUSHROOM)).generate(worldIn, rand, new BlockPos(l11, j16, i19));
            }
            if (rand.nextInt(8) == 0) {
                int i12 = k + rand.nextInt(16) + 8;
                int k16 = rand.nextInt(128);
                int j19 = l + rand.nextInt(16) + 8;
                (new WorldGenBush(Blocks.RED_MUSHROOM)).generate(worldIn, rand, new BlockPos(i12, k16, j19));
            }
            for (int j12 = 0; j12 < 10; j12++) {
                int l16 = k + rand.nextInt(16) + 8;
                int k19 = rand.nextInt(128);
                int j21 = l + rand.nextInt(16) + 8;
                (new WorldGenReed()).generate(worldIn, rand, new BlockPos(l16, k19, j21));
            }

            if (rand.nextInt(32) == 0) {
                int k12 = k + rand.nextInt(16) + 8;
                int i17 = rand.nextInt(128);
                int l19 = l + rand.nextInt(16) + 8;
                (new WorldGenPumpkin()).generate(worldIn, rand, new BlockPos(k12, i17, l19));
            }
            int l12 = 0;
            if (biome == Biomes.DESERT) {
                l12 += 10;
            }
            for (int j17 = 0; j17 < l12; j17++) {
                int i20 = k + rand.nextInt(16) + 8;
                int k21 = rand.nextInt(128);
                int k22 = l + rand.nextInt(16) + 8;
                (new WorldGenCactus()).generate(worldIn, rand, new BlockPos(i20, k21, k22));
            }

            for (int k17 = 0; k17 < 50; k17++) {
                int j20 = k + rand.nextInt(16) + 8;
                int l21 = rand.nextInt(rand.nextInt(120) + 8);
                int l22 = l + rand.nextInt(16) + 8;
                (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(worldIn, rand, new BlockPos(j20, l21, l22));
            }

            for (int l17 = 0; l17 < 20; l17++) {
                int k20 = k + rand.nextInt(16) + 8;
                int i22 = rand.nextInt(rand.nextInt(rand.nextInt(112) + 8) + 8);
                int i23 = l + rand.nextInt(16) + 8;
                (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(worldIn, rand, new BlockPos(k20, i22, i23));
            }

            for (int i18 = k + 8; i18 < k + 8 + 16; i18++) {
                for (int l20 = l + 8; l20 < l + 8 + 16; l20++) {
                    int j22 = i18 - (k + 8);
                    int j23 = l20 - (l + 8);
                    int k23 = worldIn.getTopSolidOrLiquidBlock(new BlockPos(i18, 0, l20)).getY();
                    double d1 = worldIn.getBiome(new BlockPos(j22, k23, j23)).getDefaultTemperature() - ((double) (k23 - 64) / 64D) * 0.29999999999999999D;
                    if (d1 < 0.5D && k23 > 0 && k23 < 128 && worldIn.isAirBlock(new BlockPos(i18, k23, l20)) && worldIn.getBlockState(new BlockPos(i18, k23 - 1, l20)).isTopSolid() && worldIn.getBlockState(new BlockPos(i18, k23 - 1, l20)).getMaterial() != Material.ICE) {
                        worldIn.setBlockState(new BlockPos(i18, k23, l20), Blocks.SNOW.getDefaultState());
                    }
                }

            }
            BlockSand.fallInstantly = false;
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
