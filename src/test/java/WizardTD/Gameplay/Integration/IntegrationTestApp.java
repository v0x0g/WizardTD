package WizardTD.Gameplay.Integration;

import WizardTD.*;
import processing.core.*;
import processing.data.*;
import processing.opengl.*;

import java.io.*;

@SuppressWarnings("RedundantMethodOverride")
public class IntegrationTestApp extends App {
    public IntegrationTestApp() throws AppInitException {
        super();
    }

    @Override
    public void focusGained() {
    }

    @Override
    public void focusLost() {
    }

    @Override
    public void frameRate(float fps) {
    }

    @Override
    public synchronized void redraw() {
    }

    @Override
    public PSurface getSurface() {
        return null;
    }

    @Override
    public int displayDensity() {
        return 0;
    }

    @Override
    public int displayDensity(int display) {
        return 0;
    }

    @Override
    public void pixelDensity(int density) {
        
    }

    @Override
    public void setSize(int width, int height) {
        
    }

    @Override
    public void smooth() {
        
    }

    @Override
    public void smooth(int level) {
        
    }

    @Override
    public void noSmooth() {
        
    }

    @Override
    public PGraphics getGraphics() {
        return null;
    }

    @Override
    public void start() {
        
    }

    @Override
    public void stop() {
        
    }

    @Override
    public void registerMethod(String methodName, Object target) {
        
    }

    @Override
    public void unregisterMethod(String name, Object target) {
        
    }

    @Override
    protected void handleMethods(String methodName) {
        
    }

    @Override
    protected void handleMethods(String methodName, Object[] args) {
        
    }

    @Override
    public void fullScreen() {
        
    }

    @Override
    public void fullScreen(int display) {
        
    }

    @Override
    public void fullScreen(String renderer) {
        
    }

    @Override
    public void fullScreen(String renderer, int display) {
        
    }

    @Override
    public void size(int width, int height) {
        
    }

    @Override
    public void size(int width, int height, String renderer) {
        
    }

    @Override
    public void size(int width, int height, String renderer, String path) {
        
    }

    @Override
    public PGraphics createGraphics(int w, int h) {
        return null;
    }

    @Override
    public PGraphics createGraphics(int w, int h, String renderer) {
        return null;
    }

    @Override
    public PGraphics createGraphics(int w, int h, String renderer, String path) {
        return null;
    }

    @Override
    protected PGraphics makeGraphics(int w, int h, String renderer, String path, boolean primary) {
        return null;
    }

    @Override
    protected PGraphics createPrimaryGraphics() {
        return null;
    }

    @Override
    public PImage createImage(int w, int h, int format) {
        return null;
    }

    @Override
    public void method(String name) {
        
    }

    @Override
    public void thread(String name) {
        
    }

    @Override
    public void save(String filename) {
        
    }

    @Override
    public void saveFrame() {
        
    }

    @Override
    public void saveFrame(String filename) {
        
    }

    @Override
    public String insertFrame(String what) {
        return null;
    }

    @Override
    public void cursor(int kind) {
        
    }

    @Override
    public void cursor(PImage img) {
        
    }

    @Override
    public void cursor(PImage img, int x, int y) {
        
    }

    @Override
    public void cursor() {
        
    }

    @Override
    public void noCursor() {
        
    }

    @Override
    public float noise(float x) {
        return 0;
    }

    @Override
    public float noise(float x, float y) {
        return 0;
    }

    @Override
    public float noise(float x, float y, float z) {
        return 0;
    }

    @Override
    public void noiseDetail(int lod) {
        
    }

    @Override
    public void noiseDetail(int lod, float falloff) {
        
    }

    @Override
    public void noiseSeed(long seed) {
        
    }

    @Override
    public PImage loadImage(String filename) {
        return null;
    }

    @Override
    public PImage loadImage(String filename, String extension) {
        return null;
    }

    @Override
    public PImage requestImage(String filename) {
        return null;
    }

    @Override
    public PImage requestImage(String filename, String extension) {
        return null;
    }

    @Override
    protected PImage loadImageIO(String filename) {
        return null;
    }

    @Override
    protected PImage loadImageTGA(String filename) throws IOException {
        return null;
    }

    @Override
    public XML loadXML(String filename) {
        return null;
    }

    @Override
    public XML loadXML(String filename, String options) {
        return null;
    }

    @Override
    public XML parseXML(String xmlString) {
        return null;
    }

    @Override
    public XML parseXML(String xmlString, String options) {
        return null;
    }

    @Override
    public boolean saveXML(XML xml, String filename) {
        return true;
    }

    @Override
    public boolean saveXML(XML xml, String filename, String options) {
        return true;
    }

    @Override
    public JSONObject parseJSONObject(String input) {
        return null;
    }

    @Override
    public JSONObject loadJSONObject(String filename) {
        return null;
    }

    @Override
    public boolean saveJSONObject(JSONObject json, String filename) {
        return true;
    }

    @Override
    public boolean saveJSONObject(JSONObject json, String filename, String options) {
        return true;
    }

    @Override
    public JSONArray parseJSONArray(String input) {
        return null;
    }

    @Override
    public JSONArray loadJSONArray(String filename) {
        return null;
    }

    @Override
    public boolean saveJSONArray(JSONArray json, String filename) {
        return true;
    }

    @Override
    public boolean saveJSONArray(JSONArray json, String filename, String options) {
        return true;
    }

    @Override
    public Table loadTable(String filename) {
        return null;
    }

    @Override
    public Table loadTable(String filename, String options) {
        return null;
    }

    @Override
    public boolean saveTable(Table table, String filename) {
        return true;
    }

    @Override
    public boolean saveTable(Table table, String filename, String options) {
        return true;
    }

    @Override
    public PFont loadFont(String filename) {
        return null;
    }

    @Override
    protected PFont createDefaultFont(float size) {
        return null;
    }

    @Override
    public PFont createFont(String name, float size) {
        return null;
    }

    @Override
    public PFont createFont(String name, float size, boolean smooth) {
        return null;
    }

    @Override
    public PFont createFont(String name, float size, boolean smooth, char[] charset) {
        return null;
    }

    @Override
    public void selectInput(String prompt, String callback) {
        
    }

    @Override
    public void selectInput(String prompt, String callback, File file) {
        
    }

    @Override
    public void selectInput(String prompt, String callback, File file, Object callbackObject) {
        
    }

    @Override
    public void selectOutput(String prompt, String callback) {
        
    }

    @Override
    public void selectOutput(String prompt, String callback, File file) {
        
    }

    @Override
    public void selectOutput(String prompt, String callback, File file, Object callbackObject) {
        
    }

    @Override
    public void selectFolder(String prompt, String callback) {
        
    }

    @Override
    public void selectFolder(String prompt, String callback, File file) {
        
    }

    @Override
    public void selectFolder(String prompt, String callback, File file, Object callbackObject) {
        
    }

    @Override
    public String[] listPaths(String path, String... options) {
        return null;
    }

    @Override
    public File[] listFiles(String path, String... options) {
        return null;
    }

    @Override
    public BufferedReader createReader(String filename) {
        return null;
    }

    @Override
    public PrintWriter createWriter(String filename) {
        return null;
    }

    @Override
    public InputStream createInput(String filename) {
        return null;
    }

    @Override
    public InputStream createInputRaw(String filename) {
        return null;
    }

    @Override
    public byte[] loadBytes(String filename) {
        return null;
    }

    @Override
    public String[] loadStrings(String filename) {
        return null;
    }

    @Override
    public OutputStream createOutput(String filename) {
        return null;
    }

    @Override
    public boolean saveStream(String target, String source) {
        return true;
    }

    @Override
    public boolean saveStream(File target, String source) {
        return true;
    }

    @Override
    public boolean saveStream(String target, InputStream source) {
        return true;
    }

    @Override
    public void saveBytes(String filename, byte[] data) {
        
    }

    @Override
    public void saveStrings(String filename, String[] data) {
        
    }

    @Override
    public String sketchPath() {
        return null;
    }

    @Override
    public String sketchPath(String where) {
        return null;
    }

    @Override
    public File sketchFile(String where) {
        return null;
    }

    @Override
    public String savePath(String where) {
        return null;
    }

    @Override
    public File saveFile(String where) {
        return null;
    }

    @Override
    public String dataPath(String where) {
        return null;
    }

    @Override
    public File dataFile(String where) {
        return null;
    }

    @Override
    public int lerpColor(int c1, int c2, float amt) {
        return 1;
    }

    @Override
    public void frameMoved(int x, int y) {
        
    }

    @Override
    protected void showSurface() {
        
    }

    @Override
    protected void startSurface() {
        
    }

    @Override
    protected PSurface initSurface() {
        return null;
    }

    @Override
    protected void runSketch(String[] args) {
        
    }

    @Override
    protected void runSketch() {
        
    }

    @Override
    public PGraphics beginRecord(String renderer, String filename) {
        return null;
    }

    @Override
    public void beginRecord(PGraphics recorder) {
        
    }

    @Override
    public void endRecord() {
        
    }

    @Override
    public PGraphics beginRaw(String renderer, String filename) {
        return null;
    }

    @Override
    public void beginRaw(PGraphics rawGraphics) {
        
    }

    @Override
    public void endRaw() {
        
    }

    @Override
    public void loadPixels() {
        
    }

    @Override
    public void updatePixels() {
        
    }

    @Override
    public void updatePixels(int x1, int y1, int x2, int y2) {
        
    }

    @Override
    public PGL beginPGL() {
        return null;
    }

    @Override
    public void endPGL() {
        
    }

    @Override
    public void flush() {
        
    }

    @Override
    public void hint(int which) {
        
    }

    @Override
    public void beginShape() {
        
    }

    @Override
    public void beginShape(int kind) {
        
    }

    @Override
    public void edge(boolean edge) {
        
    }

    @Override
    public void normal(float nx, float ny, float nz) {
        
    }

    @Override
    public void attribPosition(String name, float x, float y, float z) {
        
    }

    @Override
    public void attribNormal(String name, float nx, float ny, float nz) {
        
    }

    @Override
    public void attribColor(String name, int color) {
        
    }

    @Override
    public void attrib(String name, float... values) {
        
    }

    @Override
    public void attrib(String name, int... values) {
        
    }

    @Override
    public void attrib(String name, boolean... values) {
        
    }

    @Override
    public void textureMode(int mode) {
        
    }

    @Override
    public void textureWrap(int wrap) {
        
    }

    @Override
    public void texture(PImage image) {
        
    }

    @Override
    public void noTexture() {
        
    }

    @Override
    public void vertex(float x, float y) {
        
    }

    @Override
    public void vertex(float x, float y, float z) {
        
    }

    @Override
    public void vertex(float[] v) {
        
    }

    @Override
    public void vertex(float x, float y, float u, float v) {
        
    }

    @Override
    public void vertex(float x, float y, float z, float u, float v) {
        
    }

    @Override
    public void beginContour() {
        
    }

    @Override
    public void endContour() {
        
    }

    @Override
    public void endShape() {
        
    }

    @Override
    public void endShape(int mode) {
        
    }

    @Override
    public PShape loadShape(String filename) {
        return null;
    }

    @Override
    public PShape loadShape(String filename, String options) {
        return null;
    }

    @Override
    public PShape createShape() {
        return null;
    }

    @Override
    public PShape createShape(int type) {
        return null;
    }

    @Override
    public PShape createShape(int kind, float... p) {
        return null;
    }

    @Override
    public PShader loadShader(String fragFilename) {
        return null;
    }

    @Override
    public PShader loadShader(String fragFilename, String vertFilename) {
        return null;
    }

    @Override
    public void shader(PShader shader) {
        
    }

    @Override
    public void shader(PShader shader, int kind) {
        
    }

    @Override
    public void resetShader() {
        
    }

    @Override
    public void resetShader(int kind) {
        
    }

    @Override
    public void filter(PShader shader) {
        
    }

    @Override
    public void clip(float a, float b, float c, float d) {
        
    }

    @Override
    public void noClip() {
        
    }

    @Override
    public void blendMode(int mode) {
        
    }

    @Override
    public void bezierVertex(float x2, float y2, float x3, float y3, float x4, float y4) {
        
    }

    @Override
    public void bezierVertex(float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
        
    }

    @Override
    public void quadraticVertex(float cx, float cy, float x3, float y3) {
        
    }

    @Override
    public void quadraticVertex(float cx, float cy, float cz, float x3, float y3, float z3) {
        
    }

    @Override
    public void curveVertex(float x, float y) {
        
    }

    @Override
    public void curveVertex(float x, float y, float z) {
        
    }

    @Override
    public void point(float x, float y) {
        
    }

    @Override
    public void point(float x, float y, float z) {
        
    }

    @Override
    public void line(float x1, float y1, float x2, float y2) {
        
    }

    @Override
    public void line(float x1, float y1, float z1, float x2, float y2, float z2) {
        
    }

    @Override
    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        
    }

    @Override
    public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        
    }

    @Override
    public void rectMode(int mode) {
        
    }

    @Override
    public void rect(float a, float b, float c, float d) {
        
    }

    @Override
    public void rect(float a, float b, float c, float d, float r) {
        
    }

    @Override
    public void rect(float a, float b, float c, float d, float tl, float tr, float br, float bl) {
        
    }

    @Override
    public void ellipseMode(int mode) {
        
    }

    @Override
    public void ellipse(float a, float b, float c, float d) {
        
    }

    @Override
    public void arc(float a, float b, float c, float d, float start, float stop) {
        
    }

    @Override
    public void arc(float a, float b, float c, float d, float start, float stop, int mode) {
        
    }

    @Override
    public void box(float size) {
        
    }

    @Override
    public void box(float w, float h, float d) {
        
    }

    @Override
    public void sphereDetail(int res) {
        
    }

    @Override
    public void sphereDetail(int ures, int vres) {
        
    }

    @Override
    public void sphere(float r) {
        
    }

    @Override
    public float bezierPoint(float a, float b, float c, float d, float t) {
        return 0;
    }

    @Override
    public float bezierTangent(float a, float b, float c, float d, float t) {
        return 0;
    }

    @Override
    public void bezierDetail(int detail) {
        
    }

    @Override
    public void bezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        
    }

    @Override
    public void bezier(
            float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4,
            float y4, float z4) {
        
    }

    @Override
    public float curvePoint(float a, float b, float c, float d, float t) {
        return 0;
    }

    @Override
    public float curveTangent(float a, float b, float c, float d, float t) {
        return 0;
    }

    @Override
    public void curveDetail(int detail) {
        
    }

    @Override
    public void curveTightness(float tightness) {
        
    }

    @Override
    public void curve(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        
    }

    @Override
    public void curve(
            float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4,
            float y4, float z4) {
        
    }

    @Override
    public void imageMode(int mode) {
        
    }

    @Override
    public void image(PImage img, float a, float b) {
        
    }

    @Override
    public void image(PImage img, float a, float b, float c, float d) {
        
    }

    @Override
    public void image(PImage img, float a, float b, float c, float d, int u1, int v1, int u2, int v2) {
        
    }

    @Override
    public void shapeMode(int mode) {
        
    }

    @Override
    public void shape(PShape shape) {
        
    }

    @Override
    public void shape(PShape shape, float x, float y) {
        
    }

    @Override
    public void shape(PShape shape, float a, float b, float c, float d) {
        
    }

    @Override
    public void textAlign(int alignX) {
        
    }

    @Override
    public void textAlign(int alignX, int alignY) {
        
    }

    @Override
    public float textAscent() {
        return 0;
    }

    @Override
    public float textDescent() {
        return 0;
    }

    @Override
    public void textFont(PFont which) {
        
    }

    @Override
    public void textFont(PFont which, float size) {
        
    }

    @Override
    public void textLeading(float leading) {
        
    }

    @Override
    public void textMode(int mode) {
        
    }

    @Override
    public void textSize(float size) {
        
    }

    @Override
    public float textWidth(char c) {
        return 0;
    }

    @Override
    public float textWidth(String str) {
        return 0;
    }

    @Override
    public float textWidth(char[] chars, int start, int length) {
        return 0;
    }

    @Override
    public void text(char c, float x, float y) {
        
    }

    @Override
    public void text(char c, float x, float y, float z) {
        
    }

    @Override
    public void text(String str, float x, float y) {
        
    }

    @Override
    public void text(char[] chars, int start, int stop, float x, float y) {
        
    }

    @Override
    public void text(String str, float x, float y, float z) {
        
    }

    @Override
    public void text(char[] chars, int start, int stop, float x, float y, float z) {
        
    }

    @Override
    public void text(String str, float x1, float y1, float x2, float y2) {
        
    }

    @Override
    public void text(int num, float x, float y) {
        
    }

    @Override
    public void text(int num, float x, float y, float z) {
        
    }

    @Override
    public void text(float num, float x, float y) {
        
    }

    @Override
    public void text(float num, float x, float y, float z) {
        
    }

    @Override
    public void pushMatrix() {
        
    }

    @Override
    public void popMatrix() {
        
    }

    @Override
    public void translate(float x, float y) {
        
    }

    @Override
    public void translate(float x, float y, float z) {
        
    }

    @Override
    public void rotate(float angle) {
        
    }

    @Override
    public void rotateX(float angle) {
        
    }

    @Override
    public void rotateY(float angle) {
        
    }

    @Override
    public void rotateZ(float angle) {
        
    }

    @Override
    public void rotate(float angle, float x, float y, float z) {
        
    }

    @Override
    public void scale(float s) {
        
    }

    @Override
    public void scale(float x, float y) {
        
    }

    @Override
    public void scale(float x, float y, float z) {
        
    }

    @Override
    public void shearX(float angle) {
        
    }

    @Override
    public void shearY(float angle) {
        
    }

    @Override
    public void resetMatrix() {
        
    }

    @Override
    public void applyMatrix(PMatrix source) {
        
    }

    @Override
    public void applyMatrix(PMatrix2D source) {
        
    }

    @Override
    public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12) {
        
    }

    @Override
    public void applyMatrix(PMatrix3D source) {
        
    }

    @Override
    public void applyMatrix(
            float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20,
            float n21, float n22, float n23, float n30, float n31, float n32, float n33) {
        
    }

    @Override
    public PMatrix getMatrix() {
        return null;
    }

    @Override
    public PMatrix2D getMatrix(PMatrix2D target) {
        return null;
    }

    @Override
    public PMatrix3D getMatrix(PMatrix3D target) {
        return null;
    }

    @Override
    public void setMatrix(PMatrix source) {
        
    }

    @Override
    public void setMatrix(PMatrix2D source) {
        
    }

    @Override
    public void setMatrix(PMatrix3D source) {
        
    }

    @Override
    public void printMatrix() {
        
    }

    @Override
    public void beginCamera() {
        
    }

    @Override
    public void endCamera() {
        
    }

    @Override
    public void camera() {
        
    }

    @Override
    public void camera(
            float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY,
            float upZ) {
        
    }

    @Override
    public void printCamera() {
        
    }

    @Override
    public void ortho() {
        
    }

    @Override
    public void ortho(float left, float right, float bottom, float top) {
        
    }

    @Override
    public void ortho(float left, float right, float bottom, float top, float near, float far) {
        
    }

    @Override
    public void perspective() {
        
    }

    @Override
    public void perspective(float fovy, float aspect, float zNear, float zFar) {
        
    }

    @Override
    public void frustum(float left, float right, float bottom, float top, float near, float far) {
        
    }

    @Override
    public void printProjection() {
        
    }

    @Override
    public float screenX(float x, float y) {
        return 0;
    }

    @Override
    public float screenY(float x, float y) {
        return 0;
    }

    @Override
    public float screenX(float x, float y, float z) {
        return 0;
    }

    @Override
    public float screenY(float x, float y, float z) {
        return 0;
    }

    @Override
    public float screenZ(float x, float y, float z) {
        return 0;
    }

    @Override
    public float modelX(float x, float y, float z) {
        return 0;
    }

    @Override
    public float modelY(float x, float y, float z) {
        return 0;
    }

    @Override
    public float modelZ(float x, float y, float z) {
        return 0;
    }

    @Override
    public void pushStyle() {
        
    }

    @Override
    public void popStyle() {
        
    }

    @Override
    public void style(PStyle s) {
        
    }

    @Override
    public void strokeWeight(float weight) {
        
    }

    @Override
    public void strokeJoin(int join) {
        
    }

    @Override
    public void strokeCap(int cap) {
        
    }

    @Override
    public void noStroke() {
        
    }

    @Override
    public void stroke(int rgb) {
        
    }

    @Override
    public void stroke(int rgb, float alpha) {
        
    }

    @Override
    public void stroke(float gray) {
        
    }

    @Override
    public void stroke(float gray, float alpha) {
        
    }

    @Override
    public void stroke(float v1, float v2, float v3) {
        
    }

    @Override
    public void stroke(float v1, float v2, float v3, float alpha) {
        
    }

    @Override
    public void noTint() {
        
    }

    @Override
    public void tint(int rgb) {
        
    }

    @Override
    public void tint(int rgb, float alpha) {
        
    }

    @Override
    public void tint(float gray) {
        
    }

    @Override
    public void tint(float gray, float alpha) {
        
    }

    @Override
    public void tint(float v1, float v2, float v3) {
        
    }

    @Override
    public void tint(float v1, float v2, float v3, float alpha) {
        
    }

    @Override
    public void noFill() {
        
    }

    @Override
    public void fill(int rgb) {
        
    }

    @Override
    public void fill(int rgb, float alpha) {
        
    }

    @Override
    public void fill(float gray) {
        
    }

    @Override
    public void fill(float gray, float alpha) {
        
    }

    @Override
    public void fill(float v1, float v2, float v3) {
        
    }

    @Override
    public void fill(float v1, float v2, float v3, float alpha) {
        
    }

    @Override
    public void ambient(int rgb) {
        
    }

    @Override
    public void ambient(float gray) {
        
    }

    @Override
    public void ambient(float v1, float v2, float v3) {
        
    }

    @Override
    public void specular(int rgb) {
        
    }

    @Override
    public void specular(float gray) {
        
    }

    @Override
    public void specular(float v1, float v2, float v3) {
        
    }

    @Override
    public void shininess(float shine) {
        
    }

    @Override
    public void emissive(int rgb) {
        
    }

    @Override
    public void emissive(float gray) {
        
    }

    @Override
    public void emissive(float v1, float v2, float v3) {
        
    }

    @Override
    public void lights() {
        
    }

    @Override
    public void noLights() {
        
    }

    @Override
    public void ambientLight(float v1, float v2, float v3) {
        
    }

    @Override
    public void ambientLight(float v1, float v2, float v3, float x, float y, float z) {
        
    }

    @Override
    public void directionalLight(float v1, float v2, float v3, float nx, float ny, float nz) {
        
    }

    @Override
    public void pointLight(float v1, float v2, float v3, float x, float y, float z) {
        
    }

    @Override
    public void spotLight(
            float v1, float v2, float v3, float x, float y, float z, float nx, float ny, float nz, float angle,
            float concentration) {
        
    }

    @Override
    public void lightFalloff(float constant, float linear, float quadratic) {
        
    }

    @Override
    public void lightSpecular(float v1, float v2, float v3) {
        
    }

    @Override
    public void background(int rgb) {
        
    }

    @Override
    public void background(int rgb, float alpha) {
        
    }

    @Override
    public void background(float gray) {
        
    }

    @Override
    public void background(float gray, float alpha) {
        
    }

    @Override
    public void background(float v1, float v2, float v3) {
        
    }

    @Override
    public void background(float v1, float v2, float v3, float alpha) {
        
    }

    @Override
    public void clear() {
        
    }

    @Override
    public void background(PImage image) {
        
    }

    @Override
    public void colorMode(int mode) {
        
    }

    @Override
    public void colorMode(int mode, float max) {
        
    }

    @Override
    public void colorMode(int mode, float max1, float max2, float max3) {
        
    }

    @Override
    public void colorMode(int mode, float max1, float max2, float max3, float maxA) {
        
    }

    @Override
    public int get(int x, int y) {
        return 0;
    }

    @Override
    public PImage get(int x, int y, int w, int h) {
        return null;
    }

    @Override
    public PImage get() {
        return null;
    }

    @Override
    public PImage copy() {
        return null;
    }

    @Override
    public void set(int x, int y, int c) {
        
    }

    @Override
    public void set(int x, int y, PImage img) {
        
    }

    @Override
    public void mask(PImage img) {
        
    }

    @Override
    public void filter(int kind) {
        
    }

    @Override
    public void filter(int kind, float param) {
        
    }

    @Override
    public void copy(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh) {
        
    }

    @Override
    public void copy(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh) {
        
    }

    @Override
    public void blend(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode) {
        
    }

    @Override
    public void blend(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode) {
        
    }
}
