package com.iridium.iridiumcore;

import com.cryptomorin.xseries.*;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Optional;

public class XSeriesSerialSupplier {

    // These are required because XEnchants changed their enums for version 10.0.0.
    // We added the other XSeries libraries we use here in the event that they change in the future.

    /**
     * XMaterial serializer for XSeries version < 10.0.0.
     */
    public static class XMaterialSerializer extends StdSerializer<XMaterial> {

        public XMaterialSerializer() {
            super(XMaterial.class);
        }

        @Override
        public void serialize(XMaterial xMaterial, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(xMaterial.name());
        }
    }

    /**
     * XMaterial deserializer for XSeries version >= 10.0.0.
     */
    public static class XMaterialDeserializer extends StdDeserializer<XMaterial> {

        public XMaterialDeserializer() {
            super(XMaterial.class);
        }

        @Override
        public XMaterial deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String xMaterial = node.toString().replace("\"", "");
            Optional<XMaterial> optional = XMaterial.matchXMaterial(xMaterial);
            if(!optional.isPresent()){
                IridiumCore.getInstance().getLogger().warning("Could not deserialize "+xMaterial+" to a Material, defaulting to AIR");
            }
            return optional.orElse(XMaterial.AIR);
        }
    }

    /**
     * XPotion serializer for XSeries version < 10.0.0.
     */
    public static class XPotionSerializer extends StdSerializer<XPotion> {

        public XPotionSerializer() {
            super(XPotion.class);
        }

        @Override
        public void serialize(XPotion xPotion, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(xPotion.name());
        }
    }

    /**
     * XPotion deserializer for XSeries version >= 10.0.0.
     */
    public static class XPotionDeserializer extends StdDeserializer<XPotion> {

        public XPotionDeserializer() {
            super(XPotion.class);
        }

        @Override
        public XPotion deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String xPotion = node.toString().replace("\"", "");
            Optional<XPotion> optional = XPotion.of(xPotion);
            if(!optional.isPresent()){
                IridiumCore.getInstance().getLogger().warning("Could not deserialize "+xPotion+" to a Potion, defaulting to LUCK");
            }
            return optional.orElse(XPotion.LUCK);
        }
    }

    /**
     * XEnchantment serializer for XSeries version < 10.0.0.
     */
    public static class XEnchantmentSerializer extends StdSerializer<XEnchantment> {

        public XEnchantmentSerializer() {
            super(XEnchantment.class);
        }

        @Override
        public void serialize(XEnchantment xEnchantment, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(xEnchantment.name());
        }
    }

    /**
     * XEnchantment deserializer for XSeries version >= 10.0.0.
     */
    public static class XEnchantmentDeserializer extends StdDeserializer<XEnchantment> {

        public XEnchantmentDeserializer() {
            super(XEnchantment.class);
        }

        @Override
        public XEnchantment deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String xEnchantment = node.toString().replace("\"", "");
            Optional<XEnchantment> optional = XEnchantment.of(xEnchantment);
            if(!optional.isPresent()){
                IridiumCore.getInstance().getLogger().warning("Could not deserialize "+xEnchantment+" to an Enchantment, defaulting to WIND_BURST");
            }
            return optional.orElse(XEnchantment.WIND_BURST);
        }
    }

    /**
     * XBiome serializer for XSeries version < 10.0.0.
     */
    public static class XBiomeSerializer extends StdSerializer<XBiome> {

        public XBiomeSerializer() {
            super(XBiome.class);
        }

        @Override
        public void serialize(XBiome xBiome, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(xBiome.name());
        }
    }

    /**
     * XBiome deserializer for XSeries version >= 10.0.0.
     */
    public static class XBiomeDeserializer extends StdDeserializer<XBiome> {

        public XBiomeDeserializer() {
            super(XBiome.class);
        }

        @Override
        public XBiome deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String xBiome = node.toString().replace("\"", "");
            Optional<XBiome> optional = XBiome.of(xBiome);
            if(!optional.isPresent()){
                IridiumCore.getInstance().getLogger().warning("Could not deserialize "+xBiome+" to a Biome, defaulting to Plains");
            }
            return optional.orElse(XBiome.PLAINS);
        }
    }

    /**
     * XSound serializer for XSeries version < 10.0.0.
     */
    public static class XSoundSerializer extends StdSerializer<XSound> {

        public XSoundSerializer() {
            super(XSound.class);
        }

        @Override
        public void serialize(XSound xSound, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(xSound.name());
        }
    }

    /**
     * XSound deserializer for XSeries version >= 10.0.0.
     */
    public static class XSoundDeserializer extends StdDeserializer<XSound> {

        public XSoundDeserializer() {
            super(XSound.class);
        }

        @Override
        public XSound deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String xSound = node.toString().replace("\"", "");
            Optional<XSound> optional = XSound.of(xSound);
            if(!optional.isPresent()){
                IridiumCore.getInstance().getLogger().warning("Could not deserialize "+xSound+" to a Sound, defaulting to ENTITY_PLAYER_LEVELUP");
            }
            return optional.orElse(XSound.ENTITY_PLAYER_LEVELUP);
        }
    }

    /**
     * XSound serializer for XSeries version < 10.0.0.
     */
    public static class XEntityTypeSerializer extends StdSerializer<XEntityType> {

        public XEntityTypeSerializer() {
            super(XEntityType.class);
        }

        @Override
        public void serialize(XEntityType xEntityType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(xEntityType.name());
        }
    }

    /**
     * XSound deserializer for XSeries version >= 10.0.0.
     */
    public static class XEntityTypeDeserializer extends StdDeserializer<XEntityType> {

        public XEntityTypeDeserializer() {
            super(XEntityType.class);
        }

        @Override
        public XEntityType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String xEntityType = node.toString().replace("\"", "");
            Optional<XEntityType> optional = XEntityType.of(xEntityType);
            if(!optional.isPresent()){
                IridiumCore.getInstance().getLogger().warning("Could not deserialize "+xEntityType+" to an EntityType, defaulting to WOLF");
            }
            return optional.orElse(XEntityType.WOLF);
        }
    }
}
