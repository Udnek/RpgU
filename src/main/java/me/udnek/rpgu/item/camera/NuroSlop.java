package me.udnek.rpgu.item.camera;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class NuroSlop {
    // нейрослоп grok
//    /
//            * Алгоритм расчёта Ambient Occlusion (в стиле Minecraft) для точной точки попадания луча.
//            * Использует классический voxel-based подход Minecraft (проверка 3 соседних блоков на каждом углу грани + билинейная интерполяция).
//            *
//            * Возвращает значение от 0.2 (максимальное затемнение) до 1.0 (полностью освещено).
//            *
//            * @param hitPosition точная позиция попадания луча (Vector)
// * @param blockFace   нормаль грани (BlockFace)
// * @param block       блок, по которому попал луч (Block)
// * @return значение AO (0.2 — 1.0)
// */
    public static double calculateAmbientOcclusion(Vector hitPosition, BlockFace blockFace, Block block) {
        if (hitPosition == null || blockFace == null || block == null) {
            return 1.0; // fallback
        }

        // Получаем "внешний" блок (за гранью) — именно относительно него считаются окклюдеры
        Block faceBlock = block.getRelative(blockFace);

        // Определяем два перпендикулярных направления (U и V) и фракции позиции на грани
        BlockFace faceU;
        BlockFace faceV;
        double fracU;
        double fracV;

        int bx = block.getX();
        int by = block.getY();
        int bz = block.getZ();

        switch (blockFace) {
            case UP:
            case DOWN:
                faceU = BlockFace.EAST;
                faceV = BlockFace.SOUTH;
                fracU = hitPosition.getX() - bx;
                fracV = hitPosition.getZ() - bz;
                break;

            case NORTH:
            case SOUTH:
                faceU = BlockFace.EAST;
                faceV = BlockFace.UP;
                fracU = hitPosition.getX() - bx;
                fracV = hitPosition.getY() - by;
                break;

            case EAST:
            case WEST:
                faceU = BlockFace.SOUTH;
                faceV = BlockFace.UP;
                fracU = hitPosition.getZ() - bz;
                fracV = hitPosition.getY() - by;
                break;

            default:
                return 1.0;
        }

        // Зажимаем фракции на всякий случай (floating-point точность)
        fracU = Math.max(0.0, Math.min(1.0, fracU));
        fracV = Math.max(0.0, Math.min(1.0, fracV));

        // Вычисляем AO на 4 углах грани
        double ao00 = getCornerAO(faceBlock, faceU, faceV, -1, -1); // низ U, низ V
        double ao10 = getCornerAO(faceBlock, faceU, faceV,  1, -1); // высок U, низ V
        double ao01 = getCornerAO(faceBlock, faceU, faceV, -1,  1); // низ U, высок V
        double ao11 = getCornerAO(faceBlock, faceU, faceV,  1,  1); // высок U, высок V

        // Билинейная интерполяция
        double aoLowU  = (1 - fracV) * ao00 + fracV * ao01;
        double aoHighU = (1 - fracV) * ao10 + fracV * ao11;
        double ao      = (1 - fracU) * aoLowU + fracU * aoHighU;

        return ao;
    }

    ///
//        * AO для одного угла грани (проверка ровно 3 соседних блоков — как в оригинальном Minecraft).
//            */
    private static double getCornerAO(Block faceBlock, BlockFace faceU, BlockFace faceV, int sU, int sV) {
        // Направления с учётом знака угла
        BlockFace dirU = (sU > 0) ? faceU : faceU.getOppositeFace();
        BlockFace dirV = (sV > 0) ? faceV : faceV.getOppositeFace();

        Block side1  = faceBlock.getRelative(dirU);
        Block side2  = faceBlock.getRelative(dirV);
        Block corner = side1.getRelative(dirV);

        boolean o1 = side1.getType().isOccluding();
        boolean o2 = side2.getType().isOccluding();
        boolean o3 = corner.getType().isOccluding();

        int occludedCount = (o1 ? 1 : 0) + (o2 ? 1 : 0) + (o3 ? 1 : 0);

        // Формула, максимально близкая к Minecraft (мин 0.2, макс 1.0)
        return 0.2 + 0.8 * (3.0 - occludedCount) / 3.0;
    }

    /////////////////////////////////
    // нейро слоп deepseek
    /// ////////////////////////////

    private static boolean isOccluding(World world, int x, int y, int z) {
        // Проверяем, загружен ли чанк (для безопасности)
        if (!world.isChunkLoaded(x >> 4, z >> 4)) return false;
        Material type = world.getBlockAt(x, y, z).getType();
        // isOccluding() возвращает true для блоков, полностью перекрывающих свет (например, камень)
        return type.isOccluding();
    }

    private static double cornerAO(World world, int cx, int cy, int cz,
                                   int bx, int by, int bz,
                                   int dxA, int dyA, int dzA,
                                   int dxB, int dyB, int dzB) {
        int count = 0;
        // Три потенциальных соседа
        int[][] offsets = {
                {-dxA, -dyA, -dzA},
                {-dxB, -dyB, -dzB},
                {-dxA - dxB, -dyA - dyB, -dzA - dzB}
        };

        for (int[] off : offsets) {
            int nx = cx + off[0];
            int ny = cy + off[1];
            int nz = cz + off[2];

            // Пропускаем текущий блок
            if (nx == bx && ny == by && nz == bz) continue;

            if (isOccluding(world, nx, ny, nz)) {
                count++;
            }
        }
        return 1.0 - count / 3.0;
    }

    private static int[][] getCorners(int bx, int by, int bz, BlockFace face) {
        switch (face) {
            case UP:
                return new int[][]{
                        {bx,     by + 1, bz    },
                        {bx + 1, by + 1, bz    },
                        {bx,     by + 1, bz + 1},
                        {bx + 1, by + 1, bz + 1}
                };
            case DOWN:
                return new int[][]{
                        {bx,     by, bz    },
                        {bx + 1, by, bz    },
                        {bx,     by, bz + 1},
                        {bx + 1, by, bz + 1}
                };
            case NORTH:
                return new int[][]{
                        {bx,     by,     bz},
                        {bx + 1, by,     bz},
                        {bx,     by + 1, bz},
                        {bx + 1, by + 1, bz}
                };
            case SOUTH:
                return new int[][]{
                        {bx,     by,     bz + 1},
                        {bx + 1, by,     bz + 1},
                        {bx,     by + 1, bz + 1},
                        {bx + 1, by + 1, bz + 1}
                };
            case EAST:
                return new int[][]{
                        {bx + 1, by,     bz    },
                        {bx + 1, by,     bz + 1},
                        {bx + 1, by + 1, bz    },
                        {bx + 1, by + 1, bz + 1}
                };
            case WEST:
                return new int[][]{
                        {bx, by,     bz    },
                        {bx, by,     bz + 1},
                        {bx, by + 1, bz    },
                        {bx, by + 1, bz + 1}
                };
            default:
                return new int[4][3];
        }
    }

    public static double computeAO(Block block, BlockFace face, Location hit) {
        int bx = block.getX();
        int by = block.getY();
        int bz = block.getZ();
        World world = block.getWorld();

        // Определяем векторы направлений в плоскости грани
        int dxA = 0, dyA = 0, dzA = 0;
        int dxB = 0, dyB = 0, dzB = 0;

        switch (face) {
            case UP:
            case DOWN:
                dxA = 1; dzB = 1; // dirA = (1,0,0), dirB = (0,0,1)
                break;
            case NORTH:
            case SOUTH:
                dxA = 1; dyB = 1; // dirA = (1,0,0), dirB = (0,1,0)
                break;
            case EAST:
            case WEST:
                dyA = 1; dzB = 1; // dirA = (0,1,0), dirB = (0,0,1)
                break;
            default:
                return 1.0; // Неизвестная грань – считаем полностью освещённой
        }

        // Массив для хранения AO четырёх углов в порядке [00, 10, 01, 11]
        double[] aoCorners = new double[4];
        int[][] corners = getCorners(bx, by, bz, face);

        for (int i = 0; i < 4; i++) {
            int cx = corners[i][0];
            int cy = corners[i][1];
            int cz = corners[i][2];
            aoCorners[i] = cornerAO(world, cx, cy, cz, bx, by, bz, dxA, dyA, dzA, dxB, dyB, dzB);
        }

        // Относительные координаты точки на грани
        double u, v;
        switch (face) {
            case UP:
            case DOWN:
                u = hit.getX() - bx;
                v = hit.getZ() - bz;
                break;
            case NORTH:
            case SOUTH:
                u = hit.getX() - bx;
                v = hit.getY() - by;
                break;
            case EAST:
            case WEST:
                u = hit.getY() - by;
                v = hit.getZ() - bz;
                break;
            default:
                u = v = 0;
        }

        // Билинейная интерполяция
        double ao = (1 - u) * (1 - v) * aoCorners[0]
                + u * (1 - v) * aoCorners[1]
                + (1 - u) * v * aoCorners[2]
                + u * v * aoCorners[3];

        return Math.max(0, Math.min(1, ao));
    }
}
