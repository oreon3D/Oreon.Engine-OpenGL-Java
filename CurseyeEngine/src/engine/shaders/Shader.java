package engine.shaders;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

import java.util.HashMap;

import engine.buffers.BufferAllocation;
import engine.math.Matrix4f;
import engine.math.Quaternion;
import engine.math.Vec2f;
import engine.math.Vec3f;
import engine.scenegraph.GameObject;
import engine.scenegraph.components.Material;

public abstract class Shader {

	private int program;
	private HashMap<String, Integer> uniforms;
	
	public Shader()
	{
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		if (program == 0)
		{
			System.err.println("Shader creation failed");
			System.exit(1);
		}	
	}
	
	public void execute()
	{
		glUseProgram(program);
	}
	
	public void sendUniforms(Matrix4f matrix0, Matrix4f matrix1, Matrix4f matrix2){};
	
	public void sendUniforms(Matrix4f matrix0, Matrix4f matrix1){};
	
	public void sendUniforms(Matrix4f matrix){};
	
	public void sendUniforms(Material material){};
	
	public void sendUniforms(int value){};
	
	public void sendUniforms(float value){};
	
	public void sendUniforms(GameObject object){};
	
	public void sendUniforms(int l, int n, float t){};
	
	public void sendUniforms(int l, int n, int t){};
	
	public void sendUniforms(int n, int l, float a, Vec2f w, float l2) {}
	
	public void sendUniforms(int n, int l, float a, Vec2f w, float v, float l2) {}
	
	public void sendUniforms(int i, int j, int k, int l) {}
	
	public void sendUniforms(int n, int pingpong) {}
	
	
	public void addUniform(String uniform)
	{
		int uniformLocation = glGetUniformLocation(program, uniform);
		
		if (uniformLocation == 0xFFFFFFFF)
		{
			System.err.println("Error: Could not find uniform: " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
	}
	
	public void addVertexShader(String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
	}
	
	public void addGeometryShader(String text)
	{
		addProgram(text, GL_GEOMETRY_SHADER);
	}
	
	public void addFragmentShader(String text)
	{
		addProgram(text, GL_FRAGMENT_SHADER);
	}
	
	public void addTessellationControlShader(String text)
	{
		addProgram(text, GL_TESS_CONTROL_SHADER);
	}
	
	public void addTessellationEvaluationShader(String text)
	{
		addProgram(text, GL_TESS_EVALUATION_SHADER);
	}
	
	public void addComputeShader(String text)
	{
		addProgram(text, GL_COMPUTE_SHADER);
	}
	
	public void compileShader()
	{
		glLinkProgram(program);

		if(glGetProgrami(program, GL_LINK_STATUS) == 0)
		{
			System.out.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}
	
	private void addProgram(String text, int type)
	{
		int shader = glCreateShader(type);
		
		if (shader == 0)
		{
			System.err.println("Shader creation failed");
			System.exit(1);
		}	
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(program, shader);
	}
	
	public void setUniformi(String uniformName, int value)
	{
		glUniform1i(uniforms.get(uniformName), value);
	}
	public void setUniformf(String uniformName, float value)
	{
		glUniform1f(uniforms.get(uniformName), value);
	}
	public void setUniform(String uniformName, Vec2f value)
	{
		glUniform2f(uniforms.get(uniformName), value.getX(), value.getY());
	}
	public void setUniform(String uniformName, Vec3f value)
	{
		glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	public void setUniform(String uniformName, Quaternion value)
	{
		glUniform4f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ(), value.getW());
	}
	public void setUniform(String uniformName, Matrix4f value)
	{
		glUniformMatrix4(uniforms.get(uniformName), true, BufferAllocation.createFlippedBuffer(value));
	}
	
	public int getProgram()
	{
		return this.program;
	}
}
