#version 330 core

in vec2 vTexCoords;
in float vTexId;

out vec4 color;

uniform sampler2D uTextures[16];
uniform vec3 uTextColor;

void main() {
    int texId = int(vTexId);

    vec4 sampled;
    switch(texId) {
        case 0:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[0], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 1:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[1], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 2:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[2], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 3:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[3], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 4:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[4], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 5:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[5], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 6:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[6], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 7:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[7], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 8:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[8], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 9:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[9], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 10:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[10], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 11:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[11], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 12:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[12], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 13:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[13], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 14:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[14], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
        case 15:
            sampled = vec4(1.0, 1.0, 1.0, texture(uTextures[15], vTexCoords).r);
            color = vec4(uTextColor, 1.0) * sampled;
            break;
    }
}