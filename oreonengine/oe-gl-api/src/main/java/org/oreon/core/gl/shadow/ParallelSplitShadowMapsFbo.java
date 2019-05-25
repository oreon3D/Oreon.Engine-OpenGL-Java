package org.oreon.core.gl.shadow;

import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

import org.oreon.core.gl.framebuffer.GLFramebuffer;
import org.oreon.core.gl.pipeline.RenderParameter;
import org.oreon.core.gl.texture.GLTexture;
import org.oreon.core.gl.wrapper.parameter.ShadowConfig;
import org.oreon.core.gl.wrapper.texture.TextureImage2DArrray;
import org.oreon.core.image.Image.ImageFormat;
import org.oreon.core.image.Image.SamplerFilter;
import org.oreon.core.image.Image.TextureWrapMode;
import org.oreon.core.util.Constants;

public class ParallelSplitShadowMapsFbo {

	private GLFramebuffer fbo;
	private GLTexture depthMaps;
	private RenderParameter config;

	public ParallelSplitShadowMapsFbo(){
		
		config = new ShadowConfig();
		
		depthMaps = new TextureImage2DArrray(Constants.PSSM_SHADOWMAP_RESOLUTION,
				Constants.PSSM_SHADOWMAP_RESOLUTION, Constants.PSSM_SPLITS,
				ImageFormat.DEPTH32FLOAT, SamplerFilter.Bilinear, TextureWrapMode.ClampToEdge); 
		
		fbo = new GLFramebuffer();
		fbo.bind();
		glFramebufferTexture(GL_FRAMEBUFFER,
				GL_DEPTH_ATTACHMENT,
				depthMaps.getHandle(),
				0);
		glDrawBuffers(GL_NONE);
		fbo.checkStatus();
		fbo.unbind();	
	}
	
	public GLFramebuffer getFBO(){
		return fbo;
	}
	public GLTexture getDepthMaps(){
		return depthMaps;
	}

	public RenderParameter getConfig() {
		return config;
	}
	
	public void setConfig(RenderParameter config) {
		this.config = config;
	}
}
