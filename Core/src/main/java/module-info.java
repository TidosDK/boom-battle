module Core {
    requires Common;
    requires gdx;
    requires gdx.platform;
    requires gdx.backend.lwjgl3;
//    requires gdx.jnigen.loader;
    requires gdx.freetype;
    requires gdx.freetype.platform;
    requires gdx.box2d;
    requires gdx.box2d.platform;

    opens dk.sdu.mmmi.main;
}
