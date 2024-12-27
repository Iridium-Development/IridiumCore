package com.iridium.iridiumcore;

import com.cryptomorin.xseries.XBiome;
import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

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
            jsonGenerator.writeString(xMaterial.toString());
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
            String xMaterial = node.toString();
            return XMaterial.matchXMaterial(xMaterial).get();
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
            jsonGenerator.writeString(xPotion.toString());
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
            String xPotion = node.toString();
            return XPotion.matchXPotion(xPotion).get();
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
            jsonGenerator.writeString(xEnchantment.toString());
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
            String xEnchantment = node.toString();
            return XEnchantment.matchXEnchantment(xEnchantment).get();
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
            jsonGenerator.writeString(xBiome.toString());
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
            String xBiome = node.toString();
            return XBiome.of(xBiome).get();
        }
    }
}
