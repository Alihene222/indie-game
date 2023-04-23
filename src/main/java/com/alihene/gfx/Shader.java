package com.alihene.gfx;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;

public class Shader {
    private final int handle;

    public Shader(String vertexFilePath, String fragmentFilePath) {
        int vertexHandle = glCreateShader(GL_VERTEX_SHADER);
        int fragmentHandle = glCreateShader(GL_FRAGMENT_SHADER);

        String vertexSource = readFile(vertexFilePath);
        String fragmentSource = readFile(fragmentFilePath);

        int result = GL_FALSE;
        int infoLogLength;

        System.out.println("Compiling shader " + vertexFilePath);
        glShaderSource(vertexHandle, vertexSource);
        glCompileShader(vertexHandle);
        result = glGetShaderi(vertexHandle, GL_COMPILE_STATUS);
        if(result == GL_FALSE) {
            infoLogLength = glGetShaderi(vertexHandle, GL_INFO_LOG_LENGTH);
            System.out.println("Error compiling shader: " + glGetShaderInfoLog(vertexHandle, infoLogLength));
            System.exit(-1);
        }

        System.out.println("Compiling shader " + fragmentFilePath);
        glShaderSource(fragmentHandle, fragmentSource);
        glCompileShader(fragmentHandle);
        result = glGetShaderi(fragmentHandle, GL_COMPILE_STATUS);
        if(result == GL_FALSE) {
            infoLogLength = glGetShaderi(fragmentHandle, GL_INFO_LOG_LENGTH);
            System.out.println("Error compiling shader: " + glGetShaderInfoLog(fragmentHandle, infoLogLength));
            System.exit(-1);
        }

        System.out.println("Linking shader");
        handle = glCreateProgram();
        glAttachShader(handle, vertexHandle);
        glAttachShader(handle, fragmentHandle);
        glLinkProgram(handle);

        result = glGetProgrami(handle, GL_LINK_STATUS);
        if(result == GL_FALSE) {
            infoLogLength = glGetProgrami(handle, GL_INFO_LOG_LENGTH);
            System.out.println("Error linking shader: " + glGetShaderInfoLog(handle, infoLogLength));
            System.exit(-1);
        }
        glValidateProgram(handle);

        glDetachShader(handle, vertexHandle);
        glDetachShader(handle, fragmentHandle);

        glDeleteShader(vertexHandle);
        glDeleteShader(fragmentHandle);
    }

    public void use() {
        glUseProgram(handle);
    }

    public void detach() {
        glUseProgram(0);
    }

    public void setUniformMat4(String location, Matrix4f value) {
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        value.get(matrixBuffer);
        glUniformMatrix4fv(glGetUniformLocation(handle, location), false, matrixBuffer);
    }

    public void setUniformIntArray(String location, int[] value) {
        glUniform1iv(glGetUniformLocation(handle, location), value);
    }

    public void setUniformInt(String location, int value) {
        glUniform1i(glGetUniformLocation(handle, location), value);
    }

    private String readFile(String filePath) {
        StringBuilder text = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null) {
                text.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text.toString();
    }
}
