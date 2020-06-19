package nl.blocknbass.light;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Ayra506Fixture {
    public Vector3d position;
    public float targetPan, targetTilt;
    public float pan, tilt;
    public int r, g, b, a;
    public long assign_time;

    private static final float VELOCITY = 0.08f;

    public Ayra506Fixture() {
        pan = 0;
        tilt = 0;
        targetPan = 0;
        targetTilt = 0;
        assign_time = 0;
        r = 255;
        g = 255;
        b = 255;
        a = 255;
    }

    public void render(MatrixStack matrices, MinecraftClient client, float tickDelta) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableTexture();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);

        matrices.push();

        Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
        double dx = cameraPos.getX();
        double dy = cameraPos.getY();
        double dz = cameraPos.getZ();
        matrices.translate(position.x  - dx, position.y - dy, position.z - dz);
        matrices.translate(0.5, 0.5, 0.5);

        float minX = -0.5f;
        float minY = 0;
        float minZ = -0.5f;
        float maxX = 0.5f;
        float maxY = 256;
        float maxZ = 0.5f;

        float time = (client.world.getTime() - assign_time) + tickDelta;

        float dPan = (targetPan - pan) * VELOCITY * time;
        pan += dPan;

        float dTilt = (targetTilt - tilt) * VELOCITY * time;
        tilt += dTilt;
        matrices.multiply(new Quaternion(0, pan, tilt, true));

        Matrix4f matrix = matrices.peek().getModel();

        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();

        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();

        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();

        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();

        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();

        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();

        tessellator.draw();

        matrices.pop();
        RenderSystem.popMatrix();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}
