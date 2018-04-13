#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;

uniform float time;

varying vec4 vertTexCoord;

void main(void) {
  vec2 uv = vertTexCoord.st;
  uv.x += time;
  uv.x = mod(uv.x,1.);
  uv.y = 1. - uv.y;
  vec3 col = texture2D(texture,uv).xyz;
  gl_FragColor = vec4(col, 1.0);
}