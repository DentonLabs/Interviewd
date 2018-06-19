using System;
using System.Linq.Expressions;
using Interviewd.Tests.Api.Rest.LikenessExtensions.Diagnostics;
using SemanticComparison;

namespace Interviewd.Tests.Api.Rest.LikenessExtensions
{
    public static class WithInnerLikenessExtensions
    {
        public static Likeness<TSource, TDestination> WithInnerLikeness
            <TSource, TDestination, TSourceProperty, TDestinationProperty>
            (
                this Likeness<TSource, TDestination> likeness,
                Expression<Func<TDestination, TDestinationProperty>> propertyPicker,
                Expression<Func<TSource, TSourceProperty>> sourcePropertyPicker,
                Func<Likeness<TSourceProperty, TDestinationProperty>, Likeness<TSourceProperty, TDestinationProperty>> likenessDefFunc = null
            )
            where TSourceProperty : class
            where TDestinationProperty : class
        {
            return WithInnerSpecificLikeness(likeness, propertyPicker, sourcePropertyPicker, likenessDefFunc);
        }

        public static Likeness<TSource, TDestination> WithInnerSpecificLikeness
            <TSource, TDestination, TSourceProperty, TDestinationProperty, TSourcePropertySubType, TDestinationPropertySubType>
            (
                this Likeness<TSource, TDestination> likeness,
                Expression<Func<TDestination, TDestinationProperty>> propertyPicker,
                Expression<Func<TSource, TSourceProperty>> sourcePropertyPicker,
                Func<Likeness<TSourcePropertySubType, TDestinationPropertySubType>, Likeness<TSourcePropertySubType, TDestinationPropertySubType>> likenessDefFunc
            )
            where TSourceProperty : class
            where TDestinationProperty : class
            where TSourcePropertySubType : class, TSourceProperty
            where TDestinationPropertySubType : class, TDestinationProperty
        {
            return likeness.With(propertyPicker)
                .EqualsWhen((s, d) =>
                {
                    DiagnosticsWriterLocator.DiagnosticsWriter.WriteMessage(String.Format("Comparing inner properties using likeness. Source: {0} Destination: {1}.", sourcePropertyPicker, propertyPicker));

                    var sourceVal = ExpressionUtils.GetValue(sourcePropertyPicker, s);
                    var destVal = ExpressionUtils.GetValue(propertyPicker, d);


                    return CompareUtils.CompareUsingLikeness(likenessDefFunc, sourceVal, destVal);
                });
        }


    }
}