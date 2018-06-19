using System;
using System.Collections;
using System.Linq.Expressions;
using Interviewd.Tests.Api.Rest.LikenessExtensions.Diagnostics;
using SemanticComparison;

namespace Interviewd.Tests.Api.Rest.LikenessExtensions
{
    public static class WithCollectionSequenceEqualsExtensions
    {
        public static Likeness<TType, TType> WithCollectionSequenceEquals<TType>(this Likeness<TType, TType> likeness, Expression<Func<TType, IEnumerable>> propertyPicker)
        {
            return likeness.WithCollectionSequenceEquals(propertyPicker, propertyPicker);
        }

        public static Likeness<TSource, TDestination> WithCollectionSequenceEquals<TSource, TDestination>
        (
            this Likeness<TSource, TDestination> likeness,
            Expression<Func<TDestination, IEnumerable>> propertyPicker,
            Expression<Func<TSource, IEnumerable>> sourcePropertyPicker
        )
        {
            return likeness.With(propertyPicker)
                .EqualsWhen((s, d) =>
                {
                    DiagnosticsWriterLocator.DiagnosticsWriter.WriteMessage(String.Format("Comparing inner collections. Source: {0} Destination: {1}.", sourcePropertyPicker, propertyPicker));

                    var sourceVal = ExpressionUtils.GetValue(sourcePropertyPicker, s);
                    var destVal = ExpressionUtils.GetValue(propertyPicker, d);

                    return CompareUtils.CheckIfCollectionEqual(sourceVal, destVal);
                });
        }
    }
}