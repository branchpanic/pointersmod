package notjoe.pointersmod.api;

/**
 * Represents a game object (block, item) which has a registerable model that can be registered
 *  via a single method in ProxyClient
 */
public interface RegisterableModel {
    void registerModel();
}
