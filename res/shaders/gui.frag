#version 330 core

in vec2 vTexCoords;
in float vTexId;

out vec4 color;

uniform sampler2D uTextures[16];
uniform sampler2D uAtlas;

void main() {
    int texId = int(vTexId);

    switch(texId) {
        case 0:
        color = texture(uTextures[0], vTexCoords);
        break;
        case 1:
        color = texture(uTextures[1], vTexCoords);
        break;
        case 2:
        color = texture(uTextures[2], vTexCoords);
        break;
        case 3:
        color = texture(uTextures[3], vTexCoords);
        break;
        case 4:
        color = texture(uTextures[4], vTexCoords);
        break;
        case 5:
        color = texture(uTextures[5], vTexCoords);
        break;
        case 6:
        color = texture(uTextures[6], vTexCoords);
        break;
        case 7:
        color = texture(uTextures[7], vTexCoords);
        break;
        case 8:
        color = texture(uTextures[8], vTexCoords);
        break;
        case 9:
        color = texture(uTextures[9], vTexCoords);
        break;
        case 10:
        color = texture(uTextures[10], vTexCoords);
        break;
        case 11:
        color = texture(uTextures[11], vTexCoords);
        break;
        case 12:
        color = texture(uTextures[12], vTexCoords);
        break;
        case 13:
        color = texture(uTextures[13], vTexCoords);
        break;
        case 14:
        color = texture(uTextures[14], vTexCoords);
        break;
        case 15:
        color = texture(uTextures[15], vTexCoords);
        break;
        default:
        color = vec4(0.0f, 0.0f, 0.0f, 1.0f);
        break;
    }
}