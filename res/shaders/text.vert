#version 330 core

layout(location = 0) in vec2 aPos;
layout(location = 1) in vec2 aTexCoords;
layout(location = 2) in float aTexId;

out vec2 vTexCoords;
out float vTexId;

uniform mat4 uView;
uniform mat4 uProjection;

void main() {
    gl_Position = uProjection * uView * vec4(aPos, 0.0f, 1.0f);
    vTexCoords = aTexCoords;
    vTexId = aTexId;
}