<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
            <body>
                <h1>Preview</h1>
                <h2>Products</h2>
                <table border="1">
                    <tr>
                        <th>name</th>
                        <th>idCategory</th>
                        <th>price</th>
                        <th>description</th>
                        <th>amount</th>
                    </tr>
                    <xsl:for-each select="shopContent/products/product">
                        <tr>
                            <td>
                                <xsl:value-of select="name" />
                            </td>
                            <td>
                                <xsl:value-of select="idCategory" />
                            </td>
                            <td>
                                <xsl:value-of select="price" />
                            </td>
                            <td>
                                <xsl:value-of select="description" />
                            </td>
                            <td>
                                <xsl:value-of select="amount" />
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
                <h2>Categories</h2>
                <table border="1">
                    <tr>
                        <th>name</th>
                    </tr>
                    <xsl:for-each select="shopContent/categories/category">
                        <tr>
                            <td>
                                <xsl:value-of select="name" />
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>