package nl.blocknbass.light;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.client.render.RenderLayer.of;

public class Ayra506Fixture {
    public Vector3d position;
    public float targetPan, targetTilt;
    public float pan, tilt;
    public int r, g, b, a;
    public long assign_time;

    private static final float VELOCITY = 0.05f;

    public Ayra506Fixture() {
        pan = 0;
        tilt = 0;
        targetPan = 0;
        targetTilt = 0;
        assign_time = 0;
        r = 255;
        g = 255;
        b = 255;
        a = 0;
    }

    protected static final RenderPhase.Transparency TRANSLUCENT_TRANSPARENCY = new RenderPhase.Transparency("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }, () -> {
        RenderSystem.disableBlend();
    });

    protected static final RenderPhase.DiffuseLighting DISABLE_DIFFUSE_LIGHTING = new RenderPhase.DiffuseLighting(false);
    protected static final RenderPhase.Alpha ONE_TENTH_ALPHA = new RenderPhase.Alpha(0.003921569F);
    protected static final RenderPhase.Cull DISABLE_CULLING = new RenderPhase.Cull(false);
    protected static final RenderPhase.Lightmap DISABLE_LIGHTMAP = new RenderPhase.Lightmap(false);
    protected static final RenderPhase.Overlay DISABLE_OVERLAY_COLOR = new RenderPhase.Overlay(false);

    private RenderLayer getRenderLayer() {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder().transparency(TRANSLUCENT_TRANSPARENCY).diffuseLighting(DISABLE_DIFFUSE_LIGHTING).alpha(ONE_TENTH_ALPHA).cull(DISABLE_CULLING).lightmap(DISABLE_LIGHTMAP).overlay(DISABLE_OVERLAY_COLOR).build(true);
        return of("entity_translucent", VertexFormats.POSITION_COLOR, 7, 256, true, true, multiPhaseParameters);
    }

    public void render(MatrixStack matrices, MinecraftClient client, VertexConsumerProvider provider, float tickDelta) {
        VertexConsumer buffer = provider.getBuffer(getRenderLayer());

        matrices.push();

        Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
        double dx = cameraPos.getX();
        double dy = cameraPos.getY();
        double dz = cameraPos.getZ();
        matrices.translate(position.x  - dx, position.y - dy, position.z - dz);
        matrices.translate(0.5, 0.5, 0.5);

        float minX = -0.35f;
        float minY = 0;
        float minZ = -0.35f;
        float maxX = 0.35f;
        float maxY = 256;
        float maxZ = 0.35f;

        float time = (client.world.getTime() - assign_time) + tickDelta;

        float dPan = (targetPan - pan) * VELOCITY * time;
        pan += dPan;

        float dTilt = (targetTilt - tilt) * VELOCITY * time;
        tilt += dTilt;
        matrices.multiply(new Quaternion(0, pan, 180 - tilt, true));

        Matrix4f matrix = matrices.peek().getModel();

        // bottom face
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();

        // west face
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();

        // east face
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();

        // south face
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();

        // north face
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();

        // top face
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();

        matrices.pop();
    }
}
